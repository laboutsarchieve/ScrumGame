package view;


import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class MainMenuInput implements InputProcessor  {
	MainMenu game;
	boolean touching;
	float timeTouching;
	Vector2 startTouchPoint;
	Vector2 currentTouchPoint;
	
	public MainMenuInput(MainMenu game) {
		this.game = game;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		touching=true;
		game.setTouching(touching);
		startTouchPoint=new Vector2(screenX, screenY);
		currentTouchPoint = new Vector2(screenX, screenY);
		game.setStartTouch(startTouchPoint);
		game.setCurrentTouch(currentTouchPoint);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		touching=false;
		game.setTouching(touching);
		game.setCurrentTouch(new Vector2(screenX, screenY));
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		currentTouchPoint=new Vector2(screenX, screenY);
		game.setCurrentTouch(currentTouchPoint);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
