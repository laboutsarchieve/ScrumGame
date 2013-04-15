package data;

import application.MainGame;

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
	
	public boolean attemptSpawn(int range) {
		Entity newEntity = MainGame.getEntityManager().addWithin(EntityType.Villager, position, new Vector2(MainGame.getMap().getWidth()-1, MainGame.getMap().getHeight()-1), range);
		
		if(MainGame.getMap().getTileType(newEntity.getPosition()) != TileType.Grass) { 
			MainGame.getEntityManager().queueRemoveEntity(newEntity);
			return false;
		}
		else
			return true;
	}
}
