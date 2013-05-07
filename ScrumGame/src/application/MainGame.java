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
	private static SoundHelper soundHelper;
	private float timeSinceMana;

	@Override
	public void create() {		
		textureRepo = new TextureRepository();
		entityManager = new EntityManager( );
		map = Map.randomMap(50, 50);
		drawer = new Drawer(map);	
		gameInput = new GameInput(this);		

		summonHelper=new SummonHelper();
		soundHelper = new SoundHelper();
		timeSinceMana=0;
		Gdx.input.setInputProcessor(gameInput);
		GlobalGameData.getPlayer().setMana(100);
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
	int overlayKill=0;
	public void update(float deltaTime) {	
		
		timeSinceMana+=deltaTime;
		if(timeSinceMana >=.25) //tweak as you like right now its .5 mana every .25 seconds so 2 mana per second
		{
			timeSinceMana=0;
			GlobalGameData.getPlayer().addMana((float).5 + 0.1f * LevelData.getLevel());
		}
		gameInput.update(deltaTime);
		entityManager.update(deltaTime);
		entityManager.removeEntites();
	
		
		if(overlayKill>0)
		{
			overlayKill++;
		}
		if(overlayKill>90)//3 second fade in + 3 seconds on screen
		{
			overlayKill=0;
			drawer.setOverlayFadeOut(true);
		}
		
		gameInput.update(deltaTime);
		entityManager.update(deltaTime);
		entityManager.removeEntites();
		
		if(LevelData.getMonstersTillNextLevel() <= 0) {
			levelUp( );
		}
		if(LevelData.getVillagersTillGameOver() <= 0) {
			gameOver( );
		}
	}
	private boolean playOnce=true;
	private void gameOver() {
		// TODO Auto-generated method stub
		drawer.setDrawGameOver(true);
		if(playOnce){
			soundHelper.playSound(Sounds.GameOver);
			playOnce=false;
		}
	}

	private void levelUp() {
		drawer.setDrawLevelUp(true);
		overlayKill=1;
		LevelData.levelUp();
		LevelData.setMonstersTillNextLevel(LevelData.getLevel() * 10);
		LevelData.addVillagersTillGameOver(LevelData.getLevel() * 5);
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
	public static SoundHelper getSoundHelper()
	{
		return soundHelper;
	}
}
