package Data;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	protected Vector2 position;
	protected Facing facing;	
	
	public Entity(Vector2 startPosition, Facing startFacing) {
		position = startPosition;
		facing = startFacing;
	}
	
	public abstract void update(float deltaTime);
	public abstract Sprite getSprite( );
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

}
