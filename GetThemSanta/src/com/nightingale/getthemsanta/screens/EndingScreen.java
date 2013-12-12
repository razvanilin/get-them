package com.nightingale.getthemsanta.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EndingScreen implements Screen, InputProcessor{
	
	private TextureRegion backgroundTexture;
	private BitmapFont white, black;
	private Skin skin;
	private SpriteBatch spriteBatch;
	private int score;
	
	private TextButton buttonPlay;
	private Game game;


	public EndingScreen(int score, Game game){
		this.score = score;
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		spriteBatch.begin();
			spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			white.draw(spriteBatch, "Santa fainted! He fell too fast", 20, Gdx.graphics.getHeight()-20);
			white.draw(spriteBatch, "Your score: "+score, 20, Gdx.graphics.getHeight()-200);
			white.draw(spriteBatch, "Touch the screen to restart", 100, Gdx.graphics.getHeight()-400);
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void show() {
		white = new BitmapFont(Gdx.files.internal("data/fonts/white.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("data/fonts/black.fnt"), false);

		backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/sky.png")));
		spriteBatch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);

		
		/*TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");//from button.pack
		textButtonStyle.down = skin.getDrawable("button.down");//from button.pack
		textButtonStyle.pressedOffsetX = 1;//when pressed mooves to the right by 1px
		textButtonStyle.pressedOffsetY = - 1;//when pressed mooves down by 1px
		textButtonStyle.font = black;
		buttonPlay = new TextButton("PLAY AGAIN", textButtonStyle);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new GameScreen(game));
			}
		});
		buttonPlay.pad(20);
		buttonPlay.setWidth(BUTTON_WIDTH);*/
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
		spriteBatch.dispose();
		white.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		 	game.setScreen(new MenuScreen(game));
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			game.setScreen(new MenuScreen(game));
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
