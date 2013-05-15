package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import data.GameSettings;

public class SplashScreen implements Screen
{	private SpriteBatch spriteBatch;
	private Texture splashTexture;
	private TextureRegion splashTextureRegion;
	private ScreenManager myGame;

	/**
	 * Constructor for the splash screen
	 * @param g Game which called this splash screen.
	 */
	public SplashScreen(ScreenManager g)
	{
	        myGame = g;
	}
	
	@Override
	public void render(float delta)
	{
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        spriteBatch.begin();
	        spriteBatch.draw(splashTextureRegion, 0, 0, GameSettings.getScreenWidth(), GameSettings.getScreenHeight());
	        spriteBatch.end();
	        if(Gdx.input.justTouched()){
                myGame.splashDone();
	        }

	}
	
	@Override
	public void show()
	{
	        spriteBatch = new SpriteBatch();
	        splashTexture = new Texture(Gdx.files.internal("art/Splash Screen.png"));
	        splashTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
	        
	        // in the image atlas, our splash image begins at (0,0) and has a dim of 512 x 512
	        splashTextureRegion = new TextureRegion( splashTexture, 0, 0, 480, 480 );
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
        splashTexture.dispose();
	}

}