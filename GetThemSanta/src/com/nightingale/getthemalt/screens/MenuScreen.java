package com.nightingale.getthemalt.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nightingale.getthemalt.tween.ActorAccesor;
import com.nightingale.getthemalt.view.BackgroundRenderer;

public class MenuScreen implements Screen{

	private Game game;
	
	private TextureRegion backgroundTexture;
	private SpriteBatch spriteBatch;
	private BackgroundRenderer bckRenderer;

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonExit, buttonPlay;
	private Label heading;
	private TweenManager tweenManager;
	private BitmapFont title;
	
	//private float y=-100;
	
	public MenuScreen(Game game){
		this.game = game;
		bckRenderer = new BackgroundRenderer();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		//y += Gdx.graphics.getDeltaTime();
		
		stage.act(delta);
		spriteBatch.begin();
//		bckRenderer.render(-1);
		spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.end();
		stage.draw();
//		Table.drawDebug(stage);

		
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		table.invalidateHierarchy();
		table.setSize(width, height);
	}

	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/sky.png")),0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("data/ui/blueButtons.pack");
		skin = new Skin(Gdx.files.internal("data/ui/menuSkin.json"), atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//Button PLAY
		buttonPlay = new TextButton("Play", skin);
		buttonPlay.pad(20);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				 game.setScreen(new LevelScreen(game));
			}
		});
		
		//Button EXIT
		buttonExit = new TextButton("Exit", skin);
		buttonExit.pad(20);
		buttonExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		title = new BitmapFont(Gdx.files.internal("data/fonts/title.fnt"));
		LabelStyle headingStyle = new LabelStyle(title, new Color(0, 0, 0, 1));
		/*heading = new Label("Get Them Santa!", skin);*/ heading = new Label("Get Them\n    ALT", headingStyle);
		
		//putting all that into a table
		table.add(heading);
		table.getCell(heading).spaceBottom(200);
		table.row();
		table.add(buttonPlay);
		table.getCell(buttonPlay).spaceBottom(20);
		table.row();
		table.add(buttonExit);
		table.getCell(buttonExit).spaceBottom(20);
		table.debug();
		stage.addActor(table);
		
		//creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccesor());
		
		//heading and buttons fade in
		Timeline.createSequence().beginSequence()
			.push(Tween.set(buttonPlay, ActorAccesor.ALPHA).target(0))
			.push(Tween.set(buttonExit, ActorAccesor.ALPHA).target(0))
			.push(Tween.from(heading, ActorAccesor.ALPHA, 1f).target(0))
			.push(Tween.to(buttonPlay, ActorAccesor.ALPHA, .5f).target(1))
			.push(Tween.to(buttonExit, ActorAccesor.ALPHA, .5f).target(1))
			.end().start(tweenManager);
	}

	@Override
	public void hide() {
		
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
		skin.dispose();
		atlas.dispose();
		spriteBatch.dispose();
	}

}
