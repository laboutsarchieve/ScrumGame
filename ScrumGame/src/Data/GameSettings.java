package Data;

import com.badlogic.gdx.Gdx;

public class GameSettings {	
	public static float getScreenWidth() {
		return Gdx.graphics.getWidth();
	}
	public static float getScreenHeight() {
		return Gdx.graphics.getHeight();
	}
	public static float getAspectRatio( ) {
		return GameSettings.getScreenHeight( ) / GameSettings.getScreenWidth( );
	}
}
