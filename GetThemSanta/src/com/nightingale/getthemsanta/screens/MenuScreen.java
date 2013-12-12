package com.nightingale.getthemsanta.screens;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen{

	private Game game;
	
	private TextureRegion backgroundTexture;
	SpriteBatch spriteBatch;

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonExit, buttonPlay;
	private BitmapFont white, black;
	private Label heading;
	
	
	public MenuScreen(Game game){
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Table.drawDebug(stage);
		
		stage.act(delta);
		spriteBatch.begin();
		spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.end();
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/sky.png")),0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("data/ui/button.pack");
		skin = new Skin(atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//creating fonts
		black = new BitmapFont(Gdx.files.internal("data/fonts/black.fnt"), false);
		
		//creating buttons
		TextButtonStyle textButtonStyle = new TextButtonStyle();
			textButtonStyle.up = skin.getDrawable("button-blue-0");
			textButtonStyle.down = skin.getDrawable("button-blue-1");
			textButtonStyle.pressedOffsetX = 1;
			textButtonStyle.pressedOffsetY = -1;
			textButtonStyle.font = black;
		
		buttonPlay = new TextButton("Play", textButtonStyle);
		buttonPlay.pad(20);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				 game.setScreen(new GameScreen(game));
			}
		});
		buttonExit = new TextButton("Exit", textButtonStyle);
		buttonExit.pad(20);
		buttonExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		//creating heading
		LabelStyle headingStyle = new LabelStyle(black, Color.WHITE);
		
		heading = new Label("Get Them Santa!", headingStyle);
		
		//putting all that into a table
		table.add(heading);
		table.getCell(heading).spaceBottom(250);
		table.row();
		table.add(buttonPlay);
		table.row();
		table.add(buttonExit);
		table.debug();
		stage.addActor(table);
			
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		atlas.dispose();
		spriteBatch.dispose();
		black.dispose();
	}

}
