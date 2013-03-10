package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapDrawer {
	private float screenWidth;
	private float screenHeight;
	private HeightMap map;
	private final int TILE_SIZE = 32;
	public MapDrawer(HeightMap map, float screenWidth, float screenHeight) {
		this.map = map;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void draw(SpriteBatch batch) {
		float tileScreenWidth = screenWidth/TILE_SIZE;
		float tileScreenHeight = screenHeight/TILE_SIZE;
		// TODO: make this safe, it can currently go out of bounds
		for (int x = 0; x < tileScreenWidth; x++) {
			for (int y = 0; y < tileScreenHeight; y++) {
				Sprite sprite = getTileFromHeight(map.values[x][y]).getSprite();
				sprite.setPosition(x * sprite.getWidth() - 0.6f,
						y * sprite.getHeight() - 0.3f);
				sprite.draw(batch);
			}
		}
	}
	
	public SpriteHelper getTileFromHeight(float height) {
		if (height < 0.1)
			return MainGame.getTexRepo().getSprite(TextureType.grass);
		else
			return MainGame.getTexRepo().getSprite(TextureType.mountain);
	}
}
