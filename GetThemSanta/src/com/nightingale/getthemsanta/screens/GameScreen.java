package com.nightingale.getthemsanta.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nightingale.getthemsanta.controllers.SantaController;
import com.nightingale.getthemsanta.models.World;
import com.nightingale.getthemsanta.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor{

	private World world;
	private WorldRenderer renderer;
	private SantaController controller;
	private SpriteBatch spriteBatch;
	
	private int width, height;
	private boolean passed=false;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		controller.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world);
		controller = new SantaController(world);
		Gdx.input.setInputProcessor(this);

	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
		
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT)
			controller.leftPressed();
		if (keycode == Keys.RIGHT)
			controller.rightPressed();
		//System.out.println("key down");
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT){
			controller.leftReleased();
			//System.out.println("left pressed");
		}
		if (keycode == Keys.RIGHT){
			controller.rightReleased();
			//System.out.println("right pressed");
		}
		//System.out.println("Key Up");
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		/*if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		
		if (screenX < width/2)
			controller.leftPressed();
		if (screenX > width/2)
			controller.rightPressed();*/
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		/*if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		
		if (screenX < width/2)
			controller.leftReleased();
		if (screenX > width/2)
			controller.rightReleased();*/
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
