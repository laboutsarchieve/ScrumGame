package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame implements ApplicationListener {
	private static TextureRepository textureRepo;
	private OrthographicCamera camera;
	private SpriteBatch batch;	
	private HeightMap map;
	private MapDrawer mapDrawer;
	private Input input;

	@Override
	public void create() {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		textureRepo = new TextureRepository(height);
		
		map = HeightMap.randomMap(400, 400);
		
		mapDrawer = new MapDrawer(map, width, height);

		camera = new OrthographicCamera(1, height / width);
		batch = new SpriteBatch();
		
		input = new Input(this);
	}

	@Override
	public void dispose() {
		batch.dispose();
		textureRepo.dispose();
	}

	@Override
	public void render() {		
		update( );
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		mapDrawer.draw(batch);
		
		batch.end();
	}
	public void update( ) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		handleInput(deltaTime);
	}
	public void handleInput(float deltaTime) {
		
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
	
	public MapDrawer getMapDrawer() {
		return mapDrawer;
	}

	public static TextureRepository getTextureRepo() {
		return textureRepo;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public HeightMap getMap() {
		return map;
	}

	public Input getInput() {
		return input;
	}
}
