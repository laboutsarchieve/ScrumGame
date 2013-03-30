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
<<<<<<< HEAD
import com.badlogic.gdx.math.Vector2;

import data.Entity;
import data.Forest;
import data.GameSettings;
import data.Map;
import data.TileType;
import data.Village;
=======
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import data.Entity;
import data.GameData;
import data.GlobalGameData;
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
	private boolean drawBadSelection;
	private Vector2 startTouchPosition;
	private Vector2 currTouchPosition;
	private float tileScreenWidth;
	private float tileScreenHeight;
	private BitmapFont font;
	private BitmapFont bigFont;
	private CharSequence str;
	
	public Drawer(Map map) {
		this.map = map;
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
		drawVillages(deltaTime);
		drawForests(deltaTime);
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
			if(isOnScreen(monsterPos)){
				
				Sprite Empty = MainGame.getTextureRepo().getUiElement(UiElement.Health).getStepInRow(0,0);
				Sprite health = MainGame.getTextureRepo().getUiElement(UiElement.Health).getStepInRow(1,0);
				health.setScale(entity.getHitpoints()/GameData.getHitpoints(entity.getUnitType()) , 1);
				
				drawAtLocation(Empty, monsterPos.x, monsterPos.y+TILE_SIZE);
				drawAtLocation(health, monsterPos.x, monsterPos.y+TILE_SIZE);
				drawAtLocation(entity.getSprite(), monsterPos.x, monsterPos.y);
			}
		}
	}
	public void drawVillages(float deltaTime) {
		for(Village entity : MainGame.getMap().getVillages( )) {			
			Vector2 pos = entity.getPosition().cpy().mul(TILE_SIZE).sub(lowerLeftOfView);
			if(isOnScreen(pos))
				drawAtLocation(MainGame.getTextureRepo().getTile(TileType.Village).getSprite(), pos.x, pos.y);
		}
	}
	public void drawForests(float deltaTime) {
		for(Forest entity : MainGame.getMap().getForests( )) {			
			Vector2 pos = entity.getPosition().cpy().mul(TILE_SIZE).sub(lowerLeftOfView);
			if(isOnScreen(pos))
				drawAtLocation(MainGame.getTextureRepo().getTile(TileType.Forest).getSprite(), pos.x, pos.y);
		}
	}
	private boolean isOnScreen(Vector2 pos) {
		Vector2 posInPixels = pos.cpy().mul(TILE_SIZE);
		Vector2 clampedLoc = posInPixels;
		
		GameTools.clamp(clampedLoc, lowerLeftOfView, getUpperRightOfView( ));
		
		return clampedLoc.epsilonEquals(posInPixels, 2);
	}	
	public void drawUi(float deltaTime) {		//STUFF I NEED TO CHANGE
		
		int MAX=99;//Max units to show in UI
		int Critical = 25;//when to show the critical circle
		
		//GlobalGameData.getPlayer();
		float Scale= GameSettings.getAspectRatio();
		
		int Villagers=MainGame.getEntityManager().getFactionMembers(Faction.Villager).size();
		Villagers=(Villagers>MAX)? MAX : Villagers;
		int cursorSize=32;
		if(drawBadSelection)
		{
			Sprite toDraw = MainGame.getTextureRepo().getUiElement(UiElement.Circles).getStepInRow(0, 1);
			drawAtLocation(toDraw, startTouchPosition.x-cursorSize, startTouchPosition.y-cursorSize);
		}
		else if(drawMoveCenter) {
			
			
			
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
		
		for(int i=0;i<4;i++)//Draw Empty Mana Bar (background)
		{
			toDraw = MainGame.getTextureRepo().getUiElement(UiElement.ManaBar).getStepInRow(i, 0);//get Empty Mana Bar
			drawAtLocation(toDraw, new Vector2(DrawLoc.x+(i * CircleSize), DrawLoc.y));
		}
		
		//
		//	DRAW ACTUAL MANA BAR
		
		float toFill = 3 * (float)((float)GlobalGameData.getPlayer().getMana() / (float)GlobalGameData.getPlayer().getMaxMana());//sets scaling factor for drawing filled manabar
	
		toDraw = MainGame.getTextureRepo().getUiElement(UiElement.ManaBar).getStepInRow(0, 1);//get Filled Mana Bar part 1(static) 
		drawAtLocation(toDraw, new Vector2(DrawLoc.x+(0 * CircleSize), DrawLoc.y));
		
		toDraw = MainGame.getTextureRepo().getUiElement(UiElement.ManaBar).getStepInRow(1, 1);//get Filled Mana Bar part 2(dynamic)
		toDraw.setScale(toFill, 1);
		drawAtLocation(toDraw, new Vector2(DrawLoc.x+(1 * CircleSize), DrawLoc.y));
		
		
		toDraw = MainGame.getTextureRepo().getUiElement(UiElement.Circles).getStepInRow(((Villagers> Critical)? 1 : 2),0); //get OK Cirlce or Critical Circle
		drawAtLocation(toDraw, DrawLoc);
		
		//Draw Buttons (Warrior, Archer, Mage)
		for(int i=0;i<3;i++)
		{
			toDraw = MainGame.getTextureRepo().getUiElement(UiElement.Buttons).getStepInRow(i, 0);//get buttons
			drawAtLocation(toDraw, new Vector2((i * CircleSize), 0));
			
			if(MainGame.getSummonHelper().getSummonCost(SummonHelper.SummonMode.values()[i+1] ) > GlobalGameData.getPlayer().getMana() )//Red Out if not enough  mana
			{
				toDraw = MainGame.getTextureRepo().getUiElement(UiElement.Buttons).getStepInRow(4, 0);
				drawAtLocation(toDraw, new Vector2((i * CircleSize), 0));
			}
			else if(MainGame.getSummonHelper().getSummonMode() != SummonHelper.SummonMode.None)//Black out if unselected
			{
				if(MainGame.getSummonHelper().getSummonMode() != SummonHelper.SummonMode.values()[i+1] )
				{
					toDraw=MainGame.getTextureRepo().getUiElement(UiElement.Buttons).getStepInRow(3,0);
					drawAtLocation(toDraw, new Vector2((i * CircleSize), 0));
				}
			}
			
		}
		
		
		float textWidth=bigFont.getBounds("00").width;
		float textHeight=bigFont.getBounds("00").height;
		
		
		//ONLY DRAW TEXT AFTER THIS:
		Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight()); //required to render text properly
		batch.setProjectionMatrix(normalProjection);
		
		DrawLoc = new Vector2((DrawLoc.x / Scale) + ( CircleSize/Scale/2 ) - (textWidth/2), (DrawLoc.y/Scale) + ( CircleSize/Scale/2 ) + (textHeight/2));
		
		
		//font.draw(batch, "Drawing to X: "+(int)DrawLoc.x +" Y: "+ (int)DrawLoc.y, 32, 80/Scale);
		if(drawMoveCenter)//Debug Info on Touch:
		{
			font.draw(batch, "Current Touch X: "+(int)currTouchPosition.x+"Y: "+(int)currTouchPosition.y, 32,80/Scale);
			Vector2 SummonPos= startTouchPosition.cpy().add(lowerLeftOfView).div(TILE_SIZE);
			SummonPos.x=(SummonPos.x);//(float) Math.ceil(TILE_SIZE/SummonPos.x);
			SummonPos.y=(SummonPos.y);//(float) Math.ceil(TILE_SIZE/SummonPos.y);
			SummonPos.x=(float)roundUp(SummonPos.x, 1);
			SummonPos.y=(float)roundUp(SummonPos.y, 1);
			font.draw(batch, "Summon Pos X:" +SummonPos.x + " Y: "+SummonPos.y, 256/Scale, 50);
		}
		//font.scale((float) 2.3);
		bigFont.draw(batch, ((Villagers < 10 ) ? "0": "") +Integer.toString(Villagers), DrawLoc.x, DrawLoc.y);
		
		font.setColor(Color.BLACK);
		font.draw(batch, str + "Mana: "+GlobalGameData.getPlayer().getMana(), 192/Scale,25);
		
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
	double roundUp(double x, double f) {
		  return f * Math.ceil(x / f);
		}

	public boolean setButtonDraw()//Also handles summoning units. Move functionality elsewhere?
	{
		//0, 1, 2, 3 | All, Warrior, Archer, Mage
		float Scale=GameSettings.getAspectRatio();
		boolean draw=false;
		float touchX = startTouchPosition.x;
		float touchY = startTouchPosition.y;
		
		if(touchX < (64 *3) && touchY < (64 ))//touch coordinates are (not?) affected by aspect ratio
		{
			if(touchX < 64)
			{
				//set Warrior
				System.out.println("!Warrior Selected");
				MainGame.getSummonHelper().setSummonMode(SummonHelper.SummonMode.Warrior);
			}
			else if(touchX < (64*2))
			{
				//set Archer
				System.out.println("!Archer Seleted");
				MainGame.getSummonHelper().setSummonMode(SummonHelper.SummonMode.Archer);
			}
			else
			{
				//set Mage
				System.out.println("!Mage Selected");
				MainGame.getSummonHelper().setSummonMode(SummonHelper.SummonMode.Mage);
			}
			draw=true;
		}
		else
		{
			if(MainGame.getSummonHelper().getSummonMode() != SummonHelper.SummonMode.None)
			{
				Vector2 SummonPos= startTouchPosition.cpy().add(lowerLeftOfView).div(TILE_SIZE);
				SummonPos.x=(SummonPos.x);//(float) Math.ceil(TILE_SIZE/SummonPos.x);
				SummonPos.y=(SummonPos.y);//(float) Math.ceil(TILE_SIZE/SummonPos.y);
				SummonPos.x=(float)roundUp(SummonPos.x, 1)-1;
				SummonPos.y=(float)roundUp(SummonPos.y, 1)-1;
				
				if(!MainGame.getSummonHelper().SummonAtPos(SummonPos))    //ToDo: Implement: done
				{
					setDrawBadSelection(true);
				}
				draw=true;
			}
			MainGame.getSummonHelper().setSummonMode(SummonHelper.SummonMode.None);
		}
		return draw;
	}
	
	public void setDrawBadSelection(boolean draw)
	{
		this.drawBadSelection=draw;
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
