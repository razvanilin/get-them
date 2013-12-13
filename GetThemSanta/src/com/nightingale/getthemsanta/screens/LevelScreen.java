package com.nightingale.getthemsanta.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LevelScreen implements Screen {
	
	private SpriteBatch spriteBatch;
	private TextureRegion backgroundTexture;
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
			spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/sky.png")),0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

	}

}
