package view;


import application.GameTools;
import application.MainGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import data.Entity;
import data.GlobalGameData;
import data.EntityManager;
import data.Faction;
import data.GameSettings;
import data.Map;

public class Drawer {
	public final int TILE_SIZE = 32;
	private Map map;
	private Vector2 lowerLeftOfView;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private boolean drawMoveCenter;
	private Vector2 startTouchPosition;
	private Vector2 currTouchPosition;
	private float tileScreenWidth;
	private float tileScreenHeight;
	private BitmapFont font;
	private BitmapFont bigFont;
	private CharSequence str;
	private EntityManager Manager;
	
	public Drawer(Map map, EntityManager Manager) {
		Gdx.graphics.setDisplayMode(480, 800, true);
		this.map = map;
		this.Manager=Manager;
		font=new BitmapFont(Gdx.files.internal("font/Dialog.fnt"), Gdx.files.internal("font/Dialog.png"), false);
		bigFont= new BitmapFont(Gdx.files.internal("font/DialogBig.fnt"), Gdx.files.internal("font/DialogBig.png"), false);
		lowerLeftOfView = Vector2.Zero;	
		camera = new OrthographicCamera(1, GameSettings.getAspectRatio());
        camera.update();
		batch = new SpriteBatch();
		batch.enableBlending();
		str="Test Build 1.0 Width: "+GameSettings.getScreenWidth( ) +" Height: "+GameSettings.getScreenHeight( );
		Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
		batch.setProjectionMatrix(normalProjection);
	}	
	public void draw(float deltaTime) {
		setupDisplay( );
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		drawMap(deltaTime);
		drawEntities(deltaTime);
		drawUi(deltaTime);
		batch.end();
	}
	public void drawMap(float deltaTime) {		
		tileScreenWidth = GameSettings.getScreenWidth( )/TILE_SIZE;
		tileScreenHeight = GameSettings.getScreenHeight( )/TILE_SIZE;
		
		int tileOffsetX = (int)(lowerLeftOfView.x/TILE_SIZE);
		int tileOffsetY = (int)(lowerLeftOfView.y/TILE_SIZE);
		
		Vector2 viewOffset = lowerLeftOfView.cpy();
		viewOffset.sub(new Vector2(tileOffsetX*TILE_SIZE,tileOffsetY*TILE_SIZE));
		
		for (int x = 0; x < tileScreenWidth + 1; x++) {
			for (int y = 0; y < tileScreenHeight + 1; y++) {
				Vector2 position = new Vector2(x+tileOffsetX,y+tileOffsetY);
				if(!map.contains(position))
					break;
				Sprite sprite = MainGame.getTextureRepo().getTile(map.getTileType(position)).getSprite();
				drawAtLocation(sprite, x * TILE_SIZE - (int)viewOffset.x, y * TILE_SIZE - (int)viewOffset.y);
			}
		}
	}
	public void drawEntities(float deltaTime) {
		for(Entity entity : MainGame.getEntityManager( ).getEntities( )) {			
			Vector2 monsterPos = entity.getPosition().cpy().mul(TILE_SIZE).sub(lowerLeftOfView);
			if(isOnScreen(monsterPos))
				drawAtLocation(entity.getSprite(), monsterPos.x, monsterPos.y);
		}
	}
	private boolean isOnScreen(Vector2 monsterPos) {
		Vector2 monsterPosInPixels = monsterPos.cpy().mul(TILE_SIZE);
		Vector2 clampedLoc = monsterPosInPixels;
		
		GameTools.clamp(clampedLoc, lowerLeftOfView, getUpperRightOfView( ));
		
		return clampedLoc.epsilonEquals(monsterPosInPixels, 2);
	}	
	public void drawUi(float deltaTime) {		//STUFF I NEED TO CHANGE
		
		int MAX=99;//Max units to show in UI
		int Critical = 25;//when to show the critical circle
		
		//GlobalGameData.getPlayer();
		float Scale= GameSettings.getAspectRatio();
		float mana= (float) 0.8;//Test Variable for Mana Bar
		
		int Villagers=Manager.getFactionMembers(Faction.Villager).size();
		Villagers=(Villagers>MAX)? MAX : Villagers;
		
		if(drawMoveCenter) {
			
			int cursorSize=32;
			
			//First, get direction (one of 9 including no direction)
			Vector2 currentDirection=currTouchPosition.cpy();
			Vector2 cursorPos = new Vector2();//init to zero
			
			cursorPos.x=(currentDirection.x > (startTouchPosition.x+cursorSize))? 2: 0;
			cursorPos.x=(currentDirection.x < (startTouchPosition.x+cursorSize) && currentDirection.x > (startTouchPosition.x-cursorSize))? 1: cursorPos.x;
			
			cursorPos.y=(currentDirection.y > (startTouchPosition.y +cursorSize))? 0: 2;
			cursorPos.y=(currentDirection.y < (startTouchPosition.y +cursorSize) && currentDirection.y > (startTouchPosition.y-cursorSize))? 1: cursorPos.y;
			
			//Second, get appropriate cursor using math
			
			Sprite toDraw = MainGame.getTextureRepo().getUiElement(UiElement.MoveCenter).getStepInRow((int)cursorPos.x,(int)cursorPos.y);
			drawAtLocation(toDraw, new Vector2(startTouchPosition.x-(cursorSize), startTouchPosition.y-(cursorSize)));
			
		}
		//Now draw HUD
		//start with baseCircle
		int CircleSize=64;
		Sprite toDraw = MainGame.getTextureRepo().getUiElement(UiElement.Circles).getStepInCol(0, 0);//get base circle
		Vector2 DrawLoc = new Vector2(16, (camera.viewportHeight * GameSettings.getScreenHeight() - 80) );
		drawAtLocation(toDraw, DrawLoc);
		for(int i=0;i<4;i++)
		{
			toDraw = MainGame.getTextureRepo().getUiElement(UiElement.ManaBar).getStepInRow(i, 0);//get Empty Mana Bar
			drawAtLocation(toDraw, new Vector2(DrawLoc.x+(i * CircleSize), DrawLoc.y));
		}
		
		//
		//	DRAW ACTUAL MANA BAR
		float toFill = 3 * mana;
	
		toDraw = MainGame.getTextureRepo().getUiElement(UiElement.ManaBar).getStepInRow(0, 1);//get Filled Mana Bar 
		drawAtLocation(toDraw, new Vector2(DrawLoc.x+(0 * CircleSize), DrawLoc.y));
		
		toDraw = MainGame.getTextureRepo().getUiElement(UiElement.ManaBar).getStepInRow(1, 1);//get Filled Mana Bar 
		toDraw.setScale(toFill, 1);
		drawAtLocation(toDraw, new Vector2(DrawLoc.x+(1 * CircleSize), DrawLoc.y));
		
		
		toDraw = MainGame.getTextureRepo().getUiElement(UiElement.Circles).getStepInRow(((Villagers> Critical)? 1 : 2),0); //get Critical Circle
		drawAtLocation(toDraw, DrawLoc);
		//Draw Buttons (Warrior, Archer, Mage)
		for(int i=0;i<4;i++)
		{
			toDraw = MainGame.getTextureRepo().getUiElement(UiElement.Buttons).getStepInRow(i, 0);//get buttons
			drawAtLocation(toDraw, new Vector2((i * CircleSize), 0));
		}
		
		float textWidth=bigFont.getBounds("00").width;
		float textHeight=bigFont.getBounds("00").height;
		
		
		//ONLY DRAW TEXT AFTER THIS:
		Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight()); //required to render text properly
		batch.setProjectionMatrix(normalProjection);
		
		DrawLoc = new Vector2((DrawLoc.x / Scale) + ( CircleSize/Scale/2 ) - (textWidth/2), (DrawLoc.y/Scale) + ( CircleSize/Scale/2 ) + (textHeight/2));
		
		
		//font.draw(batch, "Drawing to X: "+(int)DrawLoc.x +" Y: "+ (int)DrawLoc.y, 32, 80/Scale);
		if(drawMoveCenter)
		{
			font.draw(batch, "Current Touch X: "+(int)currTouchPosition.x+"Y: "+(int)currTouchPosition.y, 32,80/Scale);
		}
		//font.scale((float) 2.3);
		bigFont.draw(batch, ((Villagers < 10 ) ? "0": "") +Integer.toString(Villagers), DrawLoc.x, DrawLoc.y);
		
		font.setColor(Color.BLACK);
		font.draw(batch, str + "Time: "+deltaTime, 192/Scale,25);
		
		//font.scale((float)-2.3);
	}
	private void drawAtLocation(Sprite sprite, Vector2 position) {
		drawAtLocation(sprite, position.x, position.y);
	}
	private void drawAtLocation(Sprite sprite, float x, float y) {
		//This weirdness is related to how aspect ratio is handled
		sprite.setPosition(x/GameSettings.getScreenHeight( ),y/GameSettings.getScreenHeight( ));
		sprite.draw(batch);
	}
	private void setupDisplay( ) {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glViewport(0, 0, (int)GameSettings.getScreenWidth( ), (int)GameSettings.getScreenHeight( ));	
		
		camera.position.set(0.5f, GameSettings.getScreenHeight() / GameSettings.getScreenWidth() / 2, 0);		
		
		camera.update();
		camera.apply(gl);
		
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
	}
	
	public void moveView(Vector2 movementInTiles) {
		lowerLeftOfView.add(movementInTiles.cpy().mul(TILE_SIZE));
		
		float maxX = map.getWidth() * TILE_SIZE - (GameSettings.getScreenWidth( ));
		float maxY = map.getHeight() * TILE_SIZE - (GameSettings.getScreenHeight( ));
		
		GameTools.clamp(lowerLeftOfView, new Vector2(0, 0), new Vector2(maxX, maxY));
	}

	public void dispose() {
		batch.dispose();
	}

	public void setDrawMoveCenter(boolean draw) {
		drawMoveCenter = draw;
		
	}
	public void setStartTouch(Vector2 startTouchPoint) {
		startTouchPosition = startTouchPoint.cpy( );		
		startTouchPosition.y = GameSettings.getScreenHeight() - startTouchPosition.y;
		startTouchPosition.mul(GameSettings.getAspectRatio());
	}
	public void setCurrTouch(Vector2 startTouchPoint) {
		currTouchPosition = startTouchPoint.cpy( );		
		currTouchPosition.y = GameSettings.getScreenHeight() - currTouchPosition.y;
		currTouchPosition.mul(GameSettings.getAspectRatio());
	}
	
	private Vector2 getUpperRightOfView() {
		return lowerLeftOfView.cpy().add(new Vector2(GameSettings.getScreenWidth( ), GameSettings.getScreenHeight( )));	
	}
}
