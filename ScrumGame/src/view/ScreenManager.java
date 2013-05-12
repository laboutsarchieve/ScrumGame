package view;

import application.MainGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class ScreenManager extends Game{
	public enum screenType{
		Splash,
		MainMenu,
		GameScreen
	}
	private static TextureRepository textureRepo;
	private static SoundHelper soundHelper;
	
	private screenType currentScreen; 
	
	private SplashScreen splashScreen;
	private MainGame gameScreen;
	private MainMenu mainMenuScreen;
	
	@Override
	public void create() {
		currentScreen=screenType.Splash;
		splashScreen = new SplashScreen(this);
		textureRepo=new TextureRepository();
		soundHelper=new SoundHelper();
		this.setScreen(splashScreen);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		float deltaTime = Gdx.graphics.getDeltaTime();
		switch(currentScreen){
		case Splash:
			splashScreen.render(deltaTime);
			break;
			
		case MainMenu:
			mainMenuScreen.render(deltaTime);
			break;
			
		case GameScreen:
			gameScreen.render(deltaTime);
			break;
		}
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
	
	public void splashDone(){
		mainMenuScreen=new MainMenu(this, soundHelper, textureRepo);
		this.setScreen(mainMenuScreen);
		splashScreen.dispose();
		currentScreen=screenType.MainMenu;
	}
	
	public void gameStart(){
		gameScreen = new MainGame(this, soundHelper, textureRepo);
		this.setScreen(gameScreen);
		currentScreen=screenType.GameScreen;
	}
	
	public void gameEnd(){
		//TODO implement
		mainMenuScreen = new MainMenu(this, soundHelper, textureRepo);
		this.setScreen(mainMenuScreen);
		currentScreen=screenType.MainMenu;
	}
	
	public void menuQuit(){
		Gdx.app.exit();
	}

}
