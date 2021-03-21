package com.neanth.game;

import com.badlogic.gdx.Game;
import com.neanth.game.screens.PlayScreen;

public class TestGame extends Game {
	
	@Override
	public void create () {
		setScreen(new PlayScreen());
	}
}
