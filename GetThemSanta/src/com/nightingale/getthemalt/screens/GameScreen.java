package com.nightingale.getthemalt.screens;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nightingale.getthemalt.controllers.SantaController;
import com.nightingale.getthemalt.models.World;
import com.nightingale.getthemalt.view.BackgroundRenderer;
import com.nightingale.getthemalt.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor{

	private World world;
	private WorldRenderer renderer;
	private BackgroundRenderer bckRenderer;
	private SantaController controller;
	private SpriteBatch spriteBatch;
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonResume, buttonMenu;
	private Label heading;
	private TweenManager tweenManager;
	private TextureRegion backgroundTexture;
	
	private Game game;
	
	public float level;
	
	private int width, height;
	
	private boolean inputSet = true;
	
	private enum GameState {
		PLAY, PAUSE
	}
	private GameState gameState;
	
	public GameScreen(Game game){
		this.game=game;
	}
	public GameScreen(Game game, float level){
		this.game = game;
		this.level = level;
	}
	
	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (gameState == GameState.PLAY){
			if (inputSet){
				Gdx.input.setInputProcessor(this);
				inputSet = false; //to set the input once
			}
//			bckRenderer.render(-1);
			renderer.render(delta);
			controller.update(renderer.velocity);
		}
		else{
			stage.act(delta);
			spriteBatch.begin();
			spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			spriteBatch.end();
			stage.draw();
			
		}
		
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, game, level);
		controller = new SantaController(world, game);
		spriteBatch = new SpriteBatch();
		
		backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/sky.png")),0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bckRenderer = new BackgroundRenderer();
		
		gameState = GameState.PLAY;

		Gdx.input.setInputProcessor(this);
	}
	
	private void drawPauseMenu(){
		//creating the Pause Menu
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("data/ui/blueButtons.pack");
		skin = new Skin(Gdx.files.internal("data/ui/menuSkin.json"), atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//create heading
		heading = new Label("Game Paused", skin);
		//create buttons
		buttonResume = new TextButton("Resume", skin);
		buttonResume.pad(20);
		buttonResume.addListener(new ClickListener(){ 
			@Override
			public void clicked(InputEvent event, float x, float y){
				gameState = GameState.PLAY;
				inputSet = true;
				dispose();
			}
		});
		buttonMenu = new TextButton("Main Menu", skin);
		buttonMenu.pad(20);
		buttonMenu.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new MenuScreen(game));
				dispose();
			}
		});
		
		table.add(heading);
		table.getCell(heading).spaceBottom(200);
		table.row();
		table.add(buttonResume);
		table.getCell(buttonResume).spaceBottom(30);
		table.row();
		table.add(buttonMenu);
		stage.addActor(table);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT)
			controller.leftPressed();
		if (keycode == Keys.RIGHT)
			controller.rightPressed();
		//System.out.println("key down");
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT){
			controller.leftReleased();
			//System.out.println("left pressed");
		}
		if (keycode == Keys.RIGHT){
			controller.rightReleased();
			//System.out.println("right pressed");
		}
		//System.out.println("Key Up");
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		/*if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		
		if (screenX < width/2)
			controller.leftPressed();
		if (screenX > width/2)
			controller.rightPressed();*/
		gameState = GameState.PAUSE;
		drawPauseMenu();
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		/*if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		
		if (screenX < width/2)
			controller.leftReleased();
		if (screenX > width/2)
			controller.rightReleased();*/
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
