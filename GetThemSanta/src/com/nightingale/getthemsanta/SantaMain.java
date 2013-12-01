package com.nightingale.getthemsanta;

import com.badlogic.gdx.Game;
import com.nightingale.getthemsanta.screens.GameScreen;

public class SantaMain extends Game {
	private GameScreen gameScreen;
	
	@Override
	public void create() {	
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}
}
