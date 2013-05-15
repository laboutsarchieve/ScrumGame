package application;

import view.*;
import data.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
public class MainGame implements Screen {
	
	private static TextureRepository textureRepo;	
	private static Map map;
	private static Drawer drawer;
	private static GameInput gameInput;
	private static EntityManager entityManager;
	private static SummonHelper summonHelper;
	private static SoundHelper soundHelper;
	private ScreenManager screenManager;
	private float timeSinceMana;

	public MainGame(ScreenManager screenMan, SoundHelper soundHelperParam, TextureRepository textureRepoParam) {	
		textureRepo = textureRepoParam;
		soundHelper = soundHelperParam;
		screenManager = screenMan;
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
	}

	@Override
	public void render(float deltaTime) {
		update(deltaTime);
		drawer.draw(deltaTime);
	}

	int tillMana = 60;
	float overlayKill=0;
	float tillGameOverEnd=0;
	public void update(float deltaTime) {	
		
		timeSinceMana+=deltaTime;
		if(timeSinceMana >=.25) //tweak as you like right now its .5 mana every .25 seconds so 2 mana per second
		{
			timeSinceMana=0;
			GlobalGameData.getPlayer().addMana((float).5f + 0.3f * LevelData.getLevel());
		}
		gameInput.update(deltaTime);
		entityManager.update(deltaTime);
		entityManager.removeEntites();
	
		//incremental loop to make overlays dissapear (Currently only kills level up overlay)
		if(overlayKill>0)
		{
			overlayKill+=deltaTime;;
		}
		if(overlayKill>1)//1 second fade in then fades out
		{
			overlayKill=0;
			drawer.setOverlayFadeOut(true);
		}
		
		//time until game over sign disappears
		if(tillGameOverEnd>0){
			tillGameOverEnd+=deltaTime;
		}
		if(tillGameOverEnd>9){//by adding deltatime, we can tune the fade in by seconds.
			LevelData.resetGame();
			screenManager.gameEnd();
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
			tillGameOverEnd=(float).01;
			playOnce=false;
		}
	}

	private void levelUp() {
		drawer.setDrawLevelUp(true);
		overlayKill=(float).01;
		LevelData.levelUp();
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


	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
