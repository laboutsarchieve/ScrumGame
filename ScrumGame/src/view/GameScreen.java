package view;

import application.LevelData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import data.EntityManager;
import data.GlobalGameData;
import data.Map;

public class GameScreen implements Screen{
	private static TextureRepository textureRepo;	
	private static Map map;
	private static Drawer drawer;
	private static GameInput gameInput;
	private static EntityManager entityManager;
	private static SummonHelper summonHelper;
	private static SoundHelper soundHelper;
	private float timeSinceMana;

	int tillMana = 60;
	int overlayKill=0;
	
	
	public GameScreen() {	
		
		textureRepo = new TextureRepository();
		entityManager = new EntityManager( );
		map = Map.randomMap(50, 50);
		drawer = new Drawer(map);	
		//gameInput = new GameInput(this);		

		summonHelper=new SummonHelper();
		soundHelper = new SoundHelper();
		timeSinceMana=0;
		Gdx.input.setInputProcessor(gameInput);
		GlobalGameData.getPlayer().setMana(100);
	}
	
	public void update(float deltaTime){

		timeSinceMana+=deltaTime;
		if(timeSinceMana >=.25) //tweak as you like right now its .5 mana every .25 seconds so 2 mana per second
		{
			timeSinceMana=0;
			GlobalGameData.getPlayer().addMana((float).5f + 0.3f * LevelData.getLevel());
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
	}
	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
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
