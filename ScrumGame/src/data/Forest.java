package data;

import com.badlogic.gdx.math.Vector2;

public class Forest {
	private Vector2 position;
	
	public Forest(Vector2 position) {
		this.position = position;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
