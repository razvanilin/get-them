package com.nightingale.getthemalt.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class World {
	
	private Santa santa;
	private Clouds cloud;
	private Gifts gift;
	
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
