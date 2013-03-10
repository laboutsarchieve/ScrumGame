package com.me.mygdxgame;

import java.util.HashMap;

public class TextureRepository {	
	HashMap<TextureType, SpriteHelper> spriteMap = new HashMap<TextureType, SpriteHelper>( );	
	float screenHeight;
	public TextureRepository(float screenHeight) {
		this.screenHeight = screenHeight;
		addToMap(TextureType.grass, "art/AWgrass.png");
		addToMap(TextureType.mountain, "art/AWmountain.png");
	}
	private void addToMap(TextureType type, String fileName) {
		spriteMap.put(type, new SpriteHelper(fileName, screenHeight));
	}
	public SpriteHelper getSprite(TextureType type) {
		return spriteMap.get(type);
	}
	public void dispose() {	
		
	}
}
