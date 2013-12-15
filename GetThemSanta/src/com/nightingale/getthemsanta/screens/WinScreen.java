package com.nightingale.getthemsanta.screens;

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
import com.nightingale.getthemsanta.tween.ActorAccesor;

public class WinScreen implements Screen{

private Game game;
	
	private TextureRegion backgroundTexture;
	private SpriteBatch spriteBatch;

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonBack;
	private Label heading, scoreLabel;
	private TweenManager tweenManager;
	
	private int score;
	
	public WinScreen(Game game, int score){
		this.game = game;
		this.score = score;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Table.drawDebug(stage);
		
		tweenManager.update(delta);
		
		stage.act(delta);
		spriteBatch.begin();
		spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.end();
		stage.draw();
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
		buttonBack = new TextButton("Play Again", skin);
		buttonBack.pad(20);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				 game.setScreen(new LevelScreen(game));
			}
		});
		
		heading = new Label("Congratulations!", skin);
		scoreLabel = new Label("Your score is: "+score, skin);
		
		//putting all that into a table
		table.add(heading);
		table.getCell(heading).spaceBottom(250);
		table.row();
		table.add(scoreLabel);
		table.getCell(scoreLabel).spaceBottom(100);
		table.row();
		table.add(buttonBack);
		stage.addActor(table);
		
		//creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccesor());
		
		//heading and buttons fade in
		Timeline.createSequence().beginSequence()
			.push(Tween.set(buttonBack, ActorAccesor.ALPHA).target(0))
			.push(Tween.set(scoreLabel, ActorAccesor.ALPHA).target(0))
			.push(Tween.from(heading, ActorAccesor.ALPHA, 1f).target(0))
			.push(Tween.to(scoreLabel, ActorAccesor.ALPHA, .5f).target(1))
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
		skin.dispose();
		atlas.dispose();
		spriteBatch.dispose();
	}

}
