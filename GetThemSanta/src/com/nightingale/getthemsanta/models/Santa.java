package com.nightingale.getthemsanta.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Santa {
	
	public enum State {
		IDLE, LEFT, RIGHT
	}
	
	private static final float SIZE = 0.5f;
	
	Vector2         position = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	Rectangle       bounds = new Rectangle();
	State           state = State.IDLE;
	
	public Santa(Vector2 position){
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	public void setPosition(Vector2 position){
		this.position = position;
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
	}
	public Vector2 getPosition(){
		return position;
	}
	
	public void setState(State newState){
		state = newState;
	}
	public State getState(){
		return state;
	}
	
	public float getSize(){
		return SIZE;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}
	public void setBounds(float ppuX, float ppuY){
		System.out.println("been Here!");
		bounds.width = SIZE*ppuX;
		bounds.height = SIZE*ppuY;
		System.out.println(bounds.width+" "+bounds.height);
	}

}
