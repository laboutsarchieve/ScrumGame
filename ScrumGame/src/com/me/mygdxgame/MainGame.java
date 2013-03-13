package com.me.mygdxgame;

import Data.EntityManager;
import Data.Facing;
import Data.HeightMap;
import Data.Monster;
import Data.TileType;
import View.Drawer;
import View.GameInput;
import View.TextureRepository;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class MainGame implements ApplicationListener {
	private static TextureRepository textureRepo;	
	private static HeightMap map;
	private static Drawer drawer;
	private static GameInput gameInput;
	private static EntityManager entityManager;

	@Override
	public void create() {		
		textureRepo = new TextureRepository();
		
		map = HeightMap.randomMap(100, 100);		
		drawer = new Drawer(map);		
		gameInput = new GameInput(this);		
		Gdx.input.setInputProcessor(gameInput);
		entityManager = new EntityManager( );
		
		addTestMonsters( );
	}
	
	public void addTestMonsters( ) {
		final int NUM_MONSTERS = 50;
		for(int x = 0; x < NUM_MONSTERS; x++) {
			Vector2 position = map.getRandomPosWithTile(TileType.Grass);
			entityManager.addEntity(new Monster(position, Facing.Down));
		}
	}

	@Override
	public void dispose() {
		drawer.dispose( );
		textureRepo.dispose();
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		update(deltaTime);
		drawer.draw(deltaTime);
	}
	public void update(float deltaTime) {
		gameInput.update(deltaTime);
		entityManager.update(deltaTime);
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
	
	//TODO: these classes should implement interfaces to limit 
	//      mutability when accessed statically like this.
	public static Drawer getMapDrawer() {
		return drawer;
	}

	public static TextureRepository getTextureRepo() {
		return textureRepo;
	}
	
	public static HeightMap getMap() {
		return map;
	}

	public static GameInput getInput() {
		return gameInput;
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}
}
