package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture grassTexture;
	private Sprite grassSprite;
	private Texture mountianTexture;
	private Sprite mountianSprite;
	private HeightMap map;
	private float w;
	private float h;
	int origX, origY;
	int newX, newY;

	int offsetX;
	int offsetY;
	boolean touch = false;
	@Override
	public void create() {		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		grassTexture = new Texture(Gdx.files.internal("art/AWgrass.png"));
		grassTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		map = new HeightMap((int)(w/32+0.5)*10,(int)(h/32+0.5)*10); 
		
		TextureRegion region = new TextureRegion(grassTexture, 0, 0, grassTexture.getWidth(), grassTexture.getHeight());
		
		grassSprite = new Sprite(region);
		grassSprite.setSize(grassSprite.getHeight()/h, grassSprite.getWidth()/h);
		grassSprite.setOrigin(grassSprite.getWidth()/2, grassSprite.getHeight()/2);
		
		mountianTexture = new Texture(Gdx.files.internal("art/AWmountian.png"));
		mountianTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(mountianTexture, 0, 0, mountianTexture.getWidth(), mountianTexture.getHeight());
		
		mountianSprite = new Sprite(region);
		mountianSprite.setSize(mountianSprite.getHeight()/h, mountianSprite.getWidth()/h);
		mountianSprite.setOrigin(mountianSprite.getWidth()/2, mountianSprite.getHeight()/2);
		
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		grassTexture.dispose();
	}

	@Override
	public void render() {

		if(Gdx.input.isTouched()) {
			newX = Gdx.input.getX();
            newY = -(Gdx.input.getY());
			if(touch == false) {
				origX = newX;
				origY = newY;
				touch = true;
			}
			else {			
				offsetX = offsetX + (newX-origX)/32;
				offsetY = offsetY + (newY-origY)/32;
				
				offsetX = Math.min(9*map.values.length/10 - offsetX - 2, offsetX);
				offsetY = Math.min(9*map.values.length/10 - offsetY - 2, offsetY);
				
				offsetX = Math.max(0, offsetX);
				offsetY = Math.max(0, offsetY);
				
				origX = newX;
				origY = newY;
			}
			
			
		}
		else {
			if(touch = true)
				origX = newX;
				origY = newY;
				touch = false;
		}
			
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(int x = 0; x < map.values.length/10; x++) {
			for(int y = 0; y < map.values[0].length/10; y++) {
				Sprite sprite = (map.values[x+offsetX][y+offsetY] < 0.1) ? grassSprite : mountianSprite;				
				sprite.setPosition(x*sprite.getWidth() - 0.6f, y*sprite.getHeight() - 0.3f);
				sprite.draw(batch);
			}
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
