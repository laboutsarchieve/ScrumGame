package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class MainGame implements ApplicationListener {
	private static TextureRepository textureRepo;	
	private HeightMap map;
	private Drawer drawer;
	private GameInput gameInput;

	@Override
	public void create() {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		textureRepo = new TextureRepository(height);
		
		map = HeightMap.randomMap(400, 400);
		
		drawer = new Drawer(map, width, height);		
		gameInput = new GameInput(this);
		Gdx.input.setInputProcessor(gameInput);
	}

	@Override
	public void dispose() {
		drawer.dispose( );
		textureRepo.dispose();
	}

	@Override
	public void render() {		
		update( );
		drawer.draw();
	}
	public void update( ) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		gameInput.update(deltaTime);
	}
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public Drawer getMapDrawer() {
		return drawer;
	}

	public static TextureRepository getTextureRepo() {
		return textureRepo;
	}
	
	public HeightMap getMap() {
		return map;
	}

	public GameInput getInput() {
		return gameInput;
	}
}
