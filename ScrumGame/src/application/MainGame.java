package application;

import view.*;
import data.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class MainGame implements ApplicationListener {
	private static TextureRepository textureRepo;	
	private static Map map;
	private static Drawer drawer;
	private static GameInput gameInput;
	private static EntityManager entityManager;
	private static SummonHelper summonHelper;
	private float time;

	@Override
	public void create() {		
		textureRepo = new TextureRepository();
		entityManager = new EntityManager( );
		map = Map.randomMap(100, 100);
		drawer = new Drawer(map);	
		gameInput = new GameInput(this);		
		summonHelper=new SummonHelper();
		time=0;
		Gdx.input.setInputProcessor(gameInput);
		GlobalGameData.getPlayer().setMana(30);
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
	int tillMana = 60;
	public void update(float deltaTime) {	
		tillMana--;
		if(tillMana < 0) {
			tillMana = 60;
			GlobalGameData.getPlayer().setMana(GlobalGameData.getPlayer().getMana()+1);
		}
		
		time+=deltaTime;
		if(time >=.25) //tweak as you like right now its .5 mana every .25 seconds so 2 mana per second
		{
			time=0;
			GlobalGameData.getPlayer().addMana((float).5);
		}
		gameInput.update(deltaTime);
		entityManager.update(deltaTime);
		entityManager.removeEntites();
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
	
	public static Map getMap() {
		return map;
	}

	public static GameInput getInput() {
		return gameInput;
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}
	public static SummonHelper getSummonHelper()
	{
		return summonHelper;
	}
}
