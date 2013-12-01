package com.nightingale.getthemsanta.controllers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nightingale.getthemsanta.models.Santa;
import com.nightingale.getthemsanta.models.Santa.State;
import com.nightingale.getthemsanta.models.World;
import com.nightingale.getthemsanta.view.WorldRenderer;

public class SantaController {
	
	enum Keys {
		LEFT, RIGHT
	}
	
	private static final float GRAVITY = -20f;
	
	private World world;
	private Santa santa;
	private WorldRenderer renderer;
	
	static HashMap<Keys, Boolean> keys = new HashMap<SantaController.Keys, Boolean>();
	static{
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
	};
	
	private float ppuX;
	private float ppuY;
	
	public SantaController(World world){
		this.world = world;
		this.santa = world.getSanta();
		renderer = new WorldRenderer(world);
	}
	
	public void leftPressed(){
		keys.get(keys.put(Keys.LEFT, true));
	}
	public void rightPressed(){
		keys.get(keys.put(Keys.RIGHT, true));
	}
	public void leftReleased(){
		keys.get(keys.put(Keys.LEFT, false));
	}
	public void rightReleased(){
		keys.get(keys.put(Keys.RIGHT, false));
	}
	
	public void update(float delta){
		processInput();
		
		santa.getAcceleration().y = GRAVITY;
		santa.getAcceleration().mul(delta);
		santa.getVelocity().add(santa.getAcceleration().x, santa.getAcceleration().y);
		
		santa.update(delta);
	}

	float accelX;
	private void processInput() {
		//System.out.println("Input");
		ppuX = renderer.ppuX;
		ppuY = renderer.ppuY;
		if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)){
				if (santa.getPosition().x >= 0 && santa.getPosition().x <=Gdx.graphics.getWidth()-(santa.getSize()*ppuX)-20){
					santa.setPosition(new Vector2(santa.getPosition().x - Gdx.input.getAccelerometerX(), santa.getPosition().y));
				}
				else if (santa.getPosition().x<=santa.getSize()*ppuX)
					santa.setPosition(new Vector2(santa.getPosition().x + 1 , santa.getPosition().y));
				else if (santa.getPosition().x>=Gdx.graphics.getWidth()-(santa.getSize()*ppuX)-20)
					santa.setPosition(new Vector2(santa.getPosition().x - 1, santa.getPosition().y));

		}
		if (keys.get(Keys.LEFT)) {
			santa.setState(State.LEFT);
			if (santa.getPosition().x >= santa.getSize()*ppuX){
				santa.getAcceleration().x = -20f;
				santa.setPosition(new Vector2(santa.getPosition().x - 20, santa.getPosition().y));
			//System.out.println("moved left");
			}
		}
		
		if (keys.get(Keys.RIGHT)) {
			santa.setState(State.RIGHT);
			if (santa.getPosition().x <= Gdx.graphics.getWidth()-(santa.getSize()*ppuX)){
				santa.getAcceleration().x = 20f;
				santa.setPosition(new Vector2(santa.getPosition().x + 20, santa.getPosition().y));
				//System.out.println("moved right");
			}
		}
		
	}
}
