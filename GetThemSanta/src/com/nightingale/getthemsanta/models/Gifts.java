package com.nightingale.getthemsanta.models;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Gifts {
	public final float SIZE = 0.7f;
	
	public ArrayList<Rectangle> gifts;
	public Texture texture;
	
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public Gifts(){
		gifts = new ArrayList<Rectangle>();
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}

	public void animate(float delta){
		for (Rectangle gift : gifts)
			gift.y += delta;
	}

	public void add(float x, float y, float ppuX, float ppuY){
		Rectangle newGift = new Rectangle();
		newGift.width = SIZE*ppuX+5;
		newGift.height = SIZE*ppuY+5;
		newGift.x = x+5;
		newGift.y = y+5;

		gifts.add(newGift);
	}
	
	public Vector2 getPosition(){
		return position;
	}
	public void setPosition(Vector2 position){
		this.position = position;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}
}
