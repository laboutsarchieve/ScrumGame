package com.me.mygdxgame;

import Data.*;
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
		
		map = HeightMap.randomMap(50, 50);		
		drawer = new Drawer(map);		
		gameInput = new GameInput(this);		
		Gdx.input.setInputProcessor(gameInput);
		entityManager = new EntityManager( );
		
		//addTestMonsters(50, 10, 10, 10, 100);
		addTestMonsters(20, 5, 5, 5, 30);
	}
	
	public void addTestMonsters(int numMonsters, int numSoldier, int numArcher, int numMage, int numVillager) {
		for(int x = 0; x < numMonsters; x++) {
			Vector2 position = map.getRandomPosWithTile(TileType.Grass);
			entityManager.addEntity(new Monster(position, Facing.Down));
		}
		for(int x = 0; x < numSoldier; x++) {
			Vector2 position = map.getRandomPosWithTile(TileType.Grass);
			entityManager.addEntity(new Soldier(position, Facing.Down));
		}
		for(int x = 0; x < numArcher; x++) {
			Vector2 position = map.getRandomPosWithTile(TileType.Grass);
			entityManager.addEntity(new Archer(position, Facing.Down));
		}
		for(int x = 0; x < numMage; x++) {
			Vector2 position = map.getRandomPosWithTile(TileType.Grass);
			entityManager.addEntity(new Mage(position, Facing.Down));
		}
		for(int x = 0; x < numVillager; x++) {
			Vector2 position = map.getRandomPosWithTile(TileType.Grass);
			entityManager.addEntity(new Villager(position, Facing.Down));
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
