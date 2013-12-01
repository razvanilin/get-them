package com.nightingale.getthemsanta.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class World {
	
	private Santa santa;
	private Clouds cloud;
	private Gifts gift;
	private static Vector2 position = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	public World(){
		createWorld();
	}
	
	private void createWorld() {
		santa = new Santa(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-100));
		cloud = new Clouds(position);
		gift = new Gifts(position);
		
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
