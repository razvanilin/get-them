package com.nightingale.getthemsanta.models;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Clouds {
	
	public final float SIZE_WIDTH = 1.3f;
	public final float SIZE_HEIGHT = 1f;
	private static final float ACCELERATION = 20f;
	
	public ArrayList<Rectangle> clouds;
	public Texture texture;
	
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public Clouds() {
		clouds = new ArrayList<Rectangle>();
		this.bounds.x = 0;
		this.bounds.y = 0;
		this.bounds.width = SIZE_WIDTH;
		this.bounds.height = SIZE_HEIGHT;
	}
	
	public void animate(float delta){
			for (Rectangle cloud : clouds)
				cloud.y += delta;
	}
	
	public void add(float x, float y, float ppuX, float ppuY){
		Rectangle newCloud = new Rectangle();
		newCloud.width = SIZE_WIDTH*ppuX;
		newCloud.height = SIZE_HEIGHT*ppuY;
		newCloud.x = x;
		newCloud.y = y;
		
		clouds.add(newCloud);
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position){
		this.position = position;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
}
