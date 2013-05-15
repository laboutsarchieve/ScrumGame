package com.me.mygdxgame;

import view.ScreenManager;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ScrumGame";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 800;
		new LwjglApplication(new ScreenManager(), cfg);
	}
}
