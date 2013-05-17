package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import data.GameSettings;

public class MainMenu implements Screen {
	//things a main menu needs: Buttons, Background, 
	public enum elements{
		Button1,
		Button2,
		Button3,
	}
	private boolean touching=false;
	private ScreenManager MyGame;
	private TextureRepository textureRepo;
	private MainMenuInput menuInput;
	private Vector2 startTouch;
	private Vector2 currentTouch;
	private SpriteBatch batch;
	private String versionString;
	private BitmapFont font;
	private BitmapFont buttonFont;
	private String[] buttonText={"Start", "Options", "Quit"};
	private boolean[] buttonClicked={false, false, false};
	private Vector2 TESTDRAW;
	
	boolean TestFlag=false;
	private OrthographicCamera camera;
	
	public MainMenu(ScreenManager g, SoundHelper soundHelperParam, TextureRepository textureRepoParam){
		MyGame=g;
		currentTouch=new Vector2(0,0);
		startTouch=new Vector2(0,0);
		textureRepo=textureRepoParam;
		menuInput=new MainMenuInput(this);
		Gdx.input.setInputProcessor(menuInput);
		font = new BitmapFont(Gdx.files.internal("font/Dialog.fnt"),
				Gdx.files.internal("font/Dialog.png"), false);
		font.setColor(Color.BLACK);
		buttonFont = new BitmapFont(Gdx.files.internal("font/DialogBig.fnt"),
				Gdx.files.internal("font/DialogBig.png"), false);
		buttonFont.setColor(Color.BLACK);
		batch = new SpriteBatch();
		batch.enableBlending();
		versionString = "Test Build 1.0 Width: " + GameSettings.getScreenWidth()
				+ " Height: " + GameSettings.getScreenHeight();
		
		camera = new OrthographicCamera(1, GameSettings.getAspectRatio());
		camera.update();
		System.out.println("Main Menu Created");
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		setupDisplay();
		camera.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0,
		//		Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //required to render sprites properly
		//batch.setProjectionMatrix(normalProjection);
		batch.begin();
		
		//Draw Background
		drawBackground();
		
		//Draw Buttons (if input falls in the draw region for a button, change the image to a 'clicked' button)
		drawButtons();
		
		//Draw Text(if you draw anything else after this, the projection matrix must be reset)
		drawText();
		batch.end();
	}
	
	public void drawBackground(){
		Sprite toDraw;
		Vector2 drawLoc=new Vector2(0,0);
		float Background_Dim=64;
		float width = (GameSettings.getScreenWidth() / Background_Dim)
				* GameSettings.getAspectRatio();
		float height = (GameSettings.getScreenHeight() / Background_Dim)
				* GameSettings.getAspectRatio();

		toDraw = textureRepo.getMainMenuElement(MainMenuElement.Background).getStepInCol(0, 0);
		toDraw.setScale(width, height);
		
		drawAtLocation(toDraw, drawLoc);
	}
	
	private void drawButtons(){
		//Draw First Button
		drawButton(0);
		
		drawButton(1);
		
		drawButton(2);
	}
	private void drawButton(int buttonNum){
		Sprite toDraw;
		Vector2 drawLoc;
		float buttonWidth, screenWidth;
		float buttonHeight, screenHeight;
		float buttonDistance, posX, posY, widthScale;
		float Scale=GameSettings.getAspectRatio();
		widthScale = 4 * Scale;
		
		toDraw = textureRepo.getMainMenuElement(MainMenuElement.Buttons).getStepInCol(0, 0);
		toDraw.setScale(widthScale,Scale);
		
		buttonWidth = toDraw.getRegionWidth()*widthScale;
		buttonHeight = toDraw.getRegionHeight()*Scale;
		
		screenHeight=GameSettings.getScreenHeight()*Scale;
		screenWidth=GameSettings.getScreenWidth()*Scale;
		
		buttonDistance = 32 * Scale;
		
		posX = ((screenWidth/2) - buttonWidth/2);
		posY = ((screenHeight-(200 * Scale)) - buttonHeight/2)-(buttonNum*(buttonHeight+buttonDistance));
		
		drawLoc=new Vector2(posX , posY);
		TESTDRAW=drawLoc.cpy();
		
		if(startTouch.x > drawLoc.x 			  && startTouch.y > drawLoc.y && 
				startTouch.x < drawLoc.x + buttonWidth && startTouch.y < drawLoc.y + buttonHeight ){
				//draw 'button clicked' thing
			toDraw = textureRepo.getMainMenuElement(MainMenuElement.Buttons).getStepInRow(1, 0);
			toDraw.setScale(widthScale,Scale);
			if(currentTouch.x > drawLoc.x 			  && currentTouch.y > drawLoc.y && 
				currentTouch.x < drawLoc.x + buttonWidth && currentTouch.y < drawLoc.y + buttonHeight){
				if(touching){
					buttonClicked[buttonNum]=true;
				}
				else{
					buttonClicked[buttonNum]=false;
				}
			}
			else{
				buttonClicked[buttonNum]=false;
			}
		}
		else{
			buttonClicked[buttonNum]=false;
		}
		
		drawAtLocation(toDraw, drawLoc);
	}
	
	private void drawText(){
		Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight()); //required to render text properly
		batch.setProjectionMatrix(normalProjection);
		float textHeight = font.getBounds(versionString).height;
		Vector2 drawLoc = new Vector2( 0 , textHeight*2 );
		font.draw(batch, versionString, drawLoc.x, drawLoc.y);
		if(touching){
			String str = "Draw Box: "+TESTDRAW;	
			font.draw(batch,  str, drawLoc.x, drawLoc.y*2);
			str = "Current Touch X:"+currentTouch.x+" Y:"+currentTouch.y;
			font.draw(batch,  str, drawLoc.x, drawLoc.y*3);
			str="Start Touch X:"+startTouch.x+" Y:"+startTouch.y;
			font.draw(batch, str, drawLoc.x, drawLoc.y*4);
		}
		for(int i=0; i<buttonClicked.length;i++){
			if(buttonClicked[i] && !touching){
				String str = "Within button region";
				font.draw(batch,  str, drawLoc.x, drawLoc.y*5);
			}
		}
		drawButtonText();
	}
	private void drawButtonText(){
		Vector2 drawLoc;
		float screenWidth;
		float buttonHeight, screenHeight;
		float buttonDistance, posX, posY;
		float textWidth=0, textHeight=0;
		
		buttonHeight = 64;
		
		screenHeight=GameSettings.getScreenHeight();
		screenWidth=GameSettings.getScreenWidth();
		
		buttonDistance = 32;
		
		posX = ((screenWidth/2) );
		for(int buttonNum=0;buttonNum < buttonText.length;buttonNum++){
		posY = ((screenHeight-(128)) - buttonHeight/2)-((buttonNum)*(buttonHeight+buttonDistance));
		textWidth = buttonFont.getBounds(buttonText[buttonNum]).width;
		textHeight = buttonFont.getBounds(buttonText[buttonNum]).height;
		
		drawLoc=new Vector2(posX-(textWidth/2) , posY-(textHeight/2));
		
		buttonFont.draw(batch, buttonText[buttonNum], drawLoc.x, drawLoc.y);
		}
	}
	
	private void drawAtLocation(Sprite sprite, Vector2 position) {
		drawAtLocation(sprite, position.x, position.y);
	}
	
	private void setupDisplay() {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glViewport(0, 0, (int) GameSettings.getScreenWidth(),
				(int) GameSettings.getScreenHeight());

		camera.position.set(0.5f,
				GameSettings.getScreenHeight() / GameSettings.getScreenWidth()
						/ 2, 0);

		camera.update();
		camera.apply(gl);

		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
	}

	private void drawAtLocation(Sprite sprite, float x, float y) {
		// This weirdness is related to how aspect ratio is handled
		sprite.setPosition(x / GameSettings.getScreenHeight(),
				y / GameSettings.getScreenHeight());
		sprite.draw(batch);
	}
	
	public void setStartTouch(Vector2 startTouchPoint) {
		startTouch = startTouchPoint.cpy();
		startTouch.y = GameSettings.getScreenHeight()
				- startTouch.y;
		startTouch.mul(GameSettings.getAspectRatio());
	}

	public void setCurrentTouch(Vector2 startTouchPoint) {
		currentTouch = startTouchPoint.cpy();
		currentTouch.y = GameSettings.getScreenHeight()
				- currentTouch.y;
		currentTouch.mul(GameSettings.getAspectRatio());
	}
	
	public void setTouching(boolean value){
		touching=value;
		if(!touching){
			if(buttonClicked[0]){
				MyGame.gameStart();
			}
			if(buttonClicked[2]){
				MyGame.menuQuit();
			}
		}
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

}
