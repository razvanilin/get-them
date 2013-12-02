package com.nightingale.getthemsanta;

import com.badlogic.gdx.Game;
import com.nightingale.getthemsanta.screens.GameScreen;
import com.nightingale.getthemsanta.screens.MenuScreen;

public class SantaMain extends Game {
	private MenuScreen menuScreen;
	
	@Override
	public void create() {	
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}
}
