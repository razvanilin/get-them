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
	Vector2         acceleration = new Vector2();
	Vector2         velocity = new Vector2();
	Rectangle       bounds = new Rectangle();
	State           state = State.IDLE;
	float			stateTime = 0;
	
	
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
	
	public void setAcceleration(Vector2 acceleration){
		this.acceleration = acceleration;
	}
	public Vector2 getAcceleration(){
		return acceleration;
	}
	
	public void setVelocity(Vector2 velocity){
		this.velocity = velocity;
	}
	public Vector2 getVelocity(){
		return velocity;
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
	
	public void update(float delta){
		stateTime+=delta;
	}
}
