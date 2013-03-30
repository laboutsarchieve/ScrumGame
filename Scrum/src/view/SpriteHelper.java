package view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import data.GameSettings;

public class SpriteHelper {
	private Texture texture;
	private Sprite sprite;
	
	public SpriteHelper(String fileName) {
		texture = new Texture(Gdx.files.internal(fileName));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.MipMapNearestNearest);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
		
		sprite = new Sprite(region);
		
		sprite.setSize(sprite.getHeight()/GameSettings.getScreenHeight( ), sprite.getWidth()/GameSettings.getScreenHeight( ));
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
	}
	
	public void dispose() {	
		texture.dispose();
	}
	
	public Texture getTexture() {
		return texture;
	}

	public Sprite getSprite() {
		return sprite;
	}
}
