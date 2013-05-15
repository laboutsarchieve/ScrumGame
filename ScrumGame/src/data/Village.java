package data;

import com.badlogic.gdx.math.Vector2;

public class Village extends SpawnTile {
	public Village(Vector2 position) {
		super(position, EntityType.Villager, Faction.Villager, 600, 5 );
	}
}
