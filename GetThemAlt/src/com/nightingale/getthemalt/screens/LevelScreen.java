package com.nightingale.getthemalt.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nightingale.getthemalt.tween.ActorAccesor;

public class LevelScreen implements Screen {
	
	private Game game;
	
	private SpriteBatch spriteBatch;
	private TextureRegion backgroundTexture;
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonLevelOne, buttonLevelTwo, buttonLevelThree, buttonBack;
	private Label heading;
	private TweenManager tweenManager;
	
	public LevelScreen(Game game){
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		stage.act(delta);
		spriteBatch.begin();
			spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.end();
		stage.draw();
		
//		Table.drawDebug(stage);

	}

	@Override
	public void resize(int width, int height) {

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
		
		//create heading
		heading = new Label("Select Challenge", skin);
		
		//create buttons
		buttonLevelOne = new TextButton("1 Minute \nChallenge", skin);
		buttonLevelOne.pad(20);
		buttonLevelOne.addListener(new ClickListener(){ 
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new GameScreen(game, 60));
				dispose();
			}
		});
		buttonLevelTwo = new TextButton("2 Minutes \nChallenge", skin);
		buttonLevelTwo.pad(20);
		buttonLevelTwo.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new GameScreen(game, 120));
				dispose();
			}
		});
		buttonLevelThree = new TextButton("Unlimited Time", skin);
		buttonLevelThree.pad(20);
		buttonLevelThree.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new GameScreen(game, -10));
				dispose();
			}
		});
		buttonBack = new TextButton("Back", skin);
		buttonBack.pad(20);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new MenuScreen(game));
			}
		});
		
		table.add(heading);
		table.getCell(heading).spaceBottom(100);
		table.row();
		table.add(buttonLevelOne);
		table.getCell(buttonLevelOne).spaceBottom(10);
		table.row();
		table.add(buttonLevelTwo);
		table.getCell(buttonLevelTwo).spaceBottom(10);
		table.row();
		table.add(buttonLevelThree);
		table.getCell(buttonLevelThree).spaceBottom(30);
		table.row();
		table.add(buttonBack);
		table.debug();
		stage.addActor(table);
		
		//creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccesor());
		
		//heading and buttons fade in
		Timeline.createSequence().beginSequence()
			.push(Tween.set(buttonLevelOne, ActorAccesor.ALPHA).target(0))
			.push(Tween.set(buttonLevelTwo, ActorAccesor.ALPHA).target(0))
			.push(Tween.set(buttonLevelThree, ActorAccesor.ALPHA).target(0))
			.push(Tween.set(buttonBack, ActorAccesor.ALPHA).target(0))
			.push(Tween.from(heading, ActorAccesor.ALPHA, .8f).target(0))
			.push(Tween.to(buttonLevelOne, ActorAccesor.ALPHA, .3f).target(1))
			.push(Tween.to(buttonLevelTwo, ActorAccesor.ALPHA, .3f).target(1))
			.push(Tween.to(buttonLevelThree, ActorAccesor.ALPHA, .3f).target(1))
			.push(Tween.to(buttonBack, ActorAccesor.ALPHA, .5f).target(1))
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
		spriteBatch.dispose();
		atlas.dispose();
		skin.dispose();
	}

}
