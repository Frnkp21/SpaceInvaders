package com.mygdx.game;

import com.badlogic.gdx.Game;

public class SpaceInvadersGame extends Game {
	@Override
	public void create () {
		setScreen(new MainMenuScreen());
	}

}
