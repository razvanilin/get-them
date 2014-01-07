package com.nightingale.getthemalt;

import com.badlogic.gdx.Game;
import com.nightingale.getthemalt.screens.MenuScreen;

public class SantaMain extends Game {
	private MenuScreen menuScreen;
	
	@Override
	public void create() {	
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}
}
