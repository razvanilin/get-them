package com.nightingale.getthemsanta.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.nightingale.getthemsanta.view.WorldRenderer;

public class World {
	
	private WorldRenderer renderer;
	
	private Santa santa;
	private Clouds cloud;
	private Gifts gift;
	private static Vector2 position = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	
	public float ppuX;
	public float ppuY;
	
	
	public World(){
		createWorld();
	}
	
	private void createWorld() {
		santa = new Santa(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-100));
		cloud = new Clouds();
		gift = new Gifts();
	}

	public Santa getSanta(){
		return santa;
	}
	
	public Clouds getCloud(){
		return cloud;
	}
	
	public Gifts getGift(){
		return gift;
	}
	
}
