package com.nightingale.getthemsanta.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BackgroundRenderer {

//	private float velocity;
	
	private TextureRegion backgroundTexture;
	private TextureRegion cloudTexture;
	
	private SpriteBatch batch;
	
	public BackgroundRenderer(){
		loadItems();
	}
	
	public void loadItems(){
		batch = new SpriteBatch();
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/backgroundModels.pack"));
		backgroundTexture = atlas.findRegion("sky");
		cloudTexture = atlas.findRegion("cloud-background");
	}
	
	public void render(float velocity){
		if (velocity == -1){
			batch.begin();
			batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(cloudTexture, -2*Gdx.graphics.getPpcX(), Gdx.graphics.getHeight()/2, 20*Gdx.graphics.getPpcX(), 8*Gdx.graphics.getPpcY());
			batch.end();
		}
	}
	
}
