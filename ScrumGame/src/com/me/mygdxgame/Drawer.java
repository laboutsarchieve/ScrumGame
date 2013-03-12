package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Drawer {
	public final int TILE_SIZE = 32;
	private HeightMap map;
	private float screenWidth;
	private float screenHeight;	
	private Vector2 lowerLeftOfView;
	private SpriteBatch batch;
	private OrthographicCamera camera; 
	
	public Drawer(HeightMap map, float screenWidth, float screenHeight) {
		this.map = map;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		lowerLeftOfView = Vector2.Zero;		
		
		camera = new OrthographicCamera(1, screenHeight / screenWidth);
        camera.update();
		batch = new SpriteBatch();
	}
	
	public void draw( ) {
		setupDisplay( );
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();		
		drawMap();		
		batch.end();
	}
	public void drawMap() {		
		float tileScreenWidth = screenWidth/TILE_SIZE;
		float tileScreenHeight = screenHeight/TILE_SIZE;
		
		int tileOffsetX = (int)(lowerLeftOfView.x/TILE_SIZE);
		int tileOffsetY = (int)(lowerLeftOfView.y/TILE_SIZE);
		
		Vector2 viewOffset = lowerLeftOfView.cpy();
		viewOffset.sub(new Vector2(tileOffsetX*TILE_SIZE,tileOffsetY*TILE_SIZE));
		
		for (int x = 0; x < tileScreenWidth; x++) {
			for (int y = 0; y < tileScreenHeight; y++) {
				if(!map.contains(x,y))
					break;
				Sprite sprite = getTileFromHeight(map.values[x+tileOffsetX][y+tileOffsetY]).getSprite();
				drawAtLocation(sprite, x * TILE_SIZE - viewOffset.x, y * TILE_SIZE - viewOffset.y);
			}
		}
	}
	private void drawAtLocation(Sprite sprite, float x, float y) {
		//This weirdness is related to how aspect ratio is handled
		sprite.setPosition(x/screenHeight,y/screenHeight);
		sprite.draw(batch);
	}
	private void setupDisplay( ) {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glViewport(0, 0, (int)screenWidth, (int)screenHeight);		
		camera.position.set(0.5f, getHeight() / getWidth() / 2, 0);		
		camera.update();
		camera.apply(gl);
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}
	public SpriteHelper getTileFromHeight(float height) {
		if (height < 0.1)
			return MainGame.getTextureRepo().getSprite(TextureType.grass);
		else
			return MainGame.getTextureRepo().getSprite(TextureType.mountain);
	}
	
	public void moveView(Vector2 movementInTiles) {
		lowerLeftOfView.add(movementInTiles.cpy().mul(TILE_SIZE));
		
		float maxX = map.getWidth() * TILE_SIZE - (screenWidth);
		float maxY = map.getHeight() * TILE_SIZE - (screenHeight);
		
		lowerLeftOfView = GameTools.clamp(lowerLeftOfView, Vector2.Zero, new Vector2(maxX, maxY));
	}

	public float getWidth() {
		return screenWidth;
	}

	public float getHeight() {
		return screenHeight;
	}

	public void dispose() {
		batch.dispose();
	}
}
