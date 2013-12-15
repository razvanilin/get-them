package com.nightingale.getthemsanta.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nightingale.getthemsanta.tween.ActorAccesor;

public class LoseScreen implements Screen{
	
	private TextureRegion backgroundTexture;
	private BitmapFont white, black;
	private Skin skin;
	private SpriteBatch spriteBatch;
	private int score;
	
	private TextButton buttonBack;
	private Label heading;
	private Label scoreLabel;
	private Game game;
	private TextureAtlas atlas;
	private Stage stage;
	private Table table;
	private TweenManager tweenManager;


	public LoseScreen(int score, Game game){
		this.score = score;
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		spriteBatch.begin();
			spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		spriteBatch.end();
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void show() {

		backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/sky.png")));
		spriteBatch = new SpriteBatch();
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("data/ui/blueButtons.pack");
		skin = new Skin(Gdx.files.internal("data/ui/menuSkin.json"), atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		heading = new Label("Santa fell too fast!", skin);
		scoreLabel = new Label("Your score was: "+score, skin);
		
		buttonBack = new TextButton("Try Again", skin);
		buttonBack.pad(20);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				 game.setScreen(new LevelScreen(game));
			}
		});
		
		table.add(heading);
		table.getCell(heading).spaceBottom(250);
		table.row();
		table.add(scoreLabel);
		table.getCell(scoreLabel).spaceBottom(100);
		table.row();
		table.add(buttonBack);
		stage.addActor(table);
		
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccesor());
		
		//heading and buttons fade in
		Timeline.createSequence().beginSequence()
			.push(Tween.set(buttonBack, ActorAccesor.ALPHA).target(0))
			.push(Tween.set(scoreLabel, ActorAccesor.ALPHA).target(0))
			.push(Tween.from(heading, ActorAccesor.ALPHA, 1f).target(0))
			.push(Tween.to(scoreLabel, ActorAccesor.ALPHA,  1f).target(1))
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
		spriteBatch.dispose();
		black.dispose();
		white.dispose();
		stage.dispose();
		atlas.dispose();
	}
}