package com.nightingale.getthemsanta.controllers;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.math.Vector2;
import com.nightingale.getthemsanta.models.Santa;
import com.nightingale.getthemsanta.models.Santa.State;
import com.nightingale.getthemsanta.models.World;
import com.nightingale.getthemsanta.view.WorldRenderer;

public class SantaController {
	
	enum Keys {
		LEFT, RIGHT
	}
	
	private World world;
	private Santa santa;
	private WorldRenderer renderer;
	
	private Game game;
	
	static HashMap<Keys, Boolean> keys = new HashMap<SantaController.Keys, Boolean>();
	static{
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
	};
	
	public SantaController(World world, Game game){
		this.game = game;
		this.world = world;
		this.santa = world.getSanta();
		renderer = new WorldRenderer();
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
	
	public void update(float velocity){
		processInput(velocity);
	}

	private void processInput(float velocity) {
//		System.out.println(Gdx.input.getAccelerometerX());
		
		//Android controller using the accelerometer
		if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)){
				if (santa.getPosition().x >= 0 && santa.getPosition().x <=Gdx.graphics.getWidth()-(santa.getSize()*renderer.ppuX)-20){
					if (Gdx.input.getAccelerometerX()<0)
						santa.setPosition(new Vector2(santa.getPosition().x - (Gdx.input.getAccelerometerX() - (velocity/25)), santa.getPosition().y));
					else if (Gdx.input.getAccelerometerX()>0)
						santa.setPosition(new Vector2(santa.getPosition().x - (Gdx.input.getAccelerometerX() + (velocity/25)), santa.getPosition().y));
				}
				//resets the char position so it does not get stuck outside of the screen
				else if (santa.getPosition().x<=santa.getSize()*renderer.ppuX)
					santa.setPosition(new Vector2(santa.getPosition().x + 1 , santa.getPosition().y));
				else if (santa.getPosition().x>=Gdx.graphics.getWidth()-(santa.getSize()*renderer.ppuX)-20)
					santa.setPosition(new Vector2(santa.getPosition().x - 1, santa.getPosition().y));

		}
		//end of android controller
		
		//Pc controller using the arrow keys
		if (keys.get(Keys.LEFT)) {
			santa.setState(State.LEFT);
			if (santa.getPosition().x >= santa.getSize()*renderer.ppuX){
				santa.setPosition(new Vector2(santa.getPosition().x - 20, santa.getPosition().y));
			}
		}
		
		if (keys.get(Keys.RIGHT)) {
			santa.setState(State.RIGHT);
			if (santa.getPosition().x <= Gdx.graphics.getWidth()-(santa.getSize()*renderer.ppuX)){
				santa.setPosition(new Vector2(santa.getPosition().x + 20, santa.getPosition().y));
			}
		}
		
		//end of pc controller
		
	}
}
