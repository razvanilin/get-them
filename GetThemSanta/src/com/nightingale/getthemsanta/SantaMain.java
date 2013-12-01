package com.nightingale.getthemsanta;

import com.badlogic.gdx.Game;
import com.nightingale.getthemsanta.screens.GameScreen;

public class SantaMain extends Game {
	
	@Override
	public void create() {		
		setScreen(new GameScreen());
	}
}
