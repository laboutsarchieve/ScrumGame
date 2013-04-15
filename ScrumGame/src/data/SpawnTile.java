package data;

import com.badlogic.gdx.math.Vector2;

public class SpawnTile {
	private Vector2 position;
	private EntityType spawnType;
	
	public SpawnTile(Vector2 position, EntityType spawnType) {
		this.position = position;
		this.spawnType = spawnType;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
