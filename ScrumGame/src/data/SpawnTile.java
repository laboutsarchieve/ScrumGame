package data;

import application.MainGame;

import com.badlogic.gdx.math.Vector2;

public class SpawnTile {
	private Vector2 position;
	private EntityType spawnType;
	private int spawnRate;
	private int tillSpawn;
	private int range;
	
	public SpawnTile(Vector2 position, EntityType spawnType, int spawnRate, int range) {
		this.position = position;
		this.spawnType = spawnType;
		this.spawnRate = spawnRate;
		this.tillSpawn = spawnRate;
		this.range = range;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public boolean tickSpawn( ) {
		tillSpawn--;
		
		if(tillSpawn < 0) {
			tillSpawn = spawnRate;
			return attemptSpawn( );
		}
		
		return false;
	}
	
	public boolean attemptSpawn() {
		Entity newEntity = MainGame.getEntityManager().addWithin(spawnType, position, new Vector2(MainGame.getMap().getWidth()-1, MainGame.getMap().getHeight()-1), range);
		
		if(MainGame.getMap().getTileType(newEntity.getPosition()) != TileType.Grass) { 
			MainGame.getEntityManager().queueRemoveEntity(newEntity);
			return false;
		}
		else
			return true;
	}
}
