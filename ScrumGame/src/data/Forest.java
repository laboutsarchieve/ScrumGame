package data;

import com.badlogic.gdx.math.Vector2;

public class Forest extends SpawnTile {
	public Forest(Vector2 position) {
		super(position, EntityType.Monster, 100, 5);
	}
}
