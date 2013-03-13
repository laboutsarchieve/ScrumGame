package View;

import Data.GameSettings;
import Data.HeightMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.mygdxgame.GameTools;
import com.me.mygdxgame.MainGame;

public class Drawer {
	public final int TILE_SIZE = 32;
	private HeightMap map;
	private Vector2 lowerLeftOfView;
	private SpriteBatch batch;
	private OrthographicCamera camera; 
	
	public Drawer(HeightMap map) {
		this.map = map;
		lowerLeftOfView = Vector2.Zero;		
		
		camera = new OrthographicCamera(1, GameSettings.getScreenHeight( ) / GameSettings.getScreenWidth( ));
        camera.update();
		batch = new SpriteBatch();
		batch.enableBlending();
	}
	
	public void draw( ) {
		setupDisplay( );		
		batch.begin();		
		drawMap();
		drawEntities( );
		batch.end();
	}
	public void drawMap() {		
		float tileScreenWidth = GameSettings.getScreenWidth( )/TILE_SIZE;
		float tileScreenHeight = GameSettings.getScreenHeight( )/TILE_SIZE;
		
		int tileOffsetX = (int)(lowerLeftOfView.x/TILE_SIZE);
		int tileOffsetY = (int)(lowerLeftOfView.y/TILE_SIZE);
		
		Vector2 viewOffset = lowerLeftOfView.cpy();
		viewOffset.sub(new Vector2(tileOffsetX*TILE_SIZE,tileOffsetY*TILE_SIZE));
		
		for (int x = 0; x < tileScreenWidth; x++) {
			for (int y = 0; y < tileScreenHeight; y++) {
				if(!map.contains(x+tileOffsetX,y+tileOffsetY))
					break;
				Sprite sprite = getTileFromHeight(map.values[x+tileOffsetX][y+tileOffsetY]).getSprite();
				drawAtLocation(sprite, x * TILE_SIZE - viewOffset.x, y * TILE_SIZE - viewOffset.y);
			}
		}
	}
	public void drawEntities() {
		SpriteSheet sheet = MainGame.getTextureRepo().getSpriteSheet(SheetType.Monster);
		for (int x = 0; x < sheet.getNumFrames(); x++) {
			Sprite sprite = sheet.getFrame(x);
			drawAtLocation(sprite, x*TILE_SIZE, 0);
		}
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
	public SpriteHelper getTileFromHeight(float height) {
		if (height < 0.1)
			return MainGame.getTextureRepo().getTile(TextureType.grass);
		else
			return MainGame.getTextureRepo().getTile(TextureType.mountain);
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
}
