package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame implements ApplicationListener {
	private static TextureRepository textureRepo;
	private OrthographicCamera camera;
	private SpriteBatch batch;	
	private HeightMap map;
	private MapDrawer mapDrawer;
	private float width;
	private float height;

	@Override
	public void create() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		textureRepo = new TextureRepository(height);
		
		map = new HeightMap(400, 400);
		mapDrawer = new MapDrawer(map, width, height);

		camera = new OrthographicCamera(1, height / width);
		batch = new SpriteBatch();
	}

	@Override
	public void dispose() {
		batch.dispose();
		textureRepo.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		mapDrawer.draw(batch);
		
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
	
	public static TextureRepository getTexRepo( ) {
		return textureRepo;
	}
}
