package data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import application.GameException;
import application.GameTools;
import application.VectorCompare;

import com.badlogic.gdx.math.*;

public class EntityManager {
	HashMap<Faction, LinkedList<Entity>> factionLists = new HashMap<Faction, LinkedList<Entity>>();
	LinkedList<Entity> entityList = new LinkedList<Entity>();
	LinkedList<Entity> toRemoveList = new LinkedList<Entity>();
	private Set<Vector2> entityPositionSet = new TreeSet<Vector2>(
			new VectorCompare());
	private List<SpawnTile> spawnTileList = new LinkedList<SpawnTile>( );

	public EntityManager() {
		factionLists.put(Faction.Player, new LinkedList<Entity>());
		factionLists.put(Faction.Villager, new LinkedList<Entity>());
		factionLists.put(Faction.Monster, new LinkedList<Entity>());
	}
	
	public void addSpawnTile(SpawnTile spawner) {
		spawnTileList.add(spawner);
	}

	public void addEntity(Entity toAdd) {
		entityList.add(toAdd);
		factionLists.get(toAdd.getFaction()).add(toAdd);
		entityPositionSet.add(toAdd.position);
	}

	public void removeEntites() {
		entityPositionSet.clear();
		for (Entity entity : toRemoveList) {
			entityPositionSet.remove(entity.position);
			entityList.remove(entity);
			factionLists.get(entity.getFaction()).remove(entity);
		}
		toRemoveList.clear();
	}

	public LinkedList<Entity> getFactionMembers(Faction faction) {
		return factionLists.get(faction);
	}

	public Entity getClosestType(Entity e, EntityType t, Faction f) {
		Entity closest = null;
		LinkedList<Entity> mobs = getFactionMembers(f);

		Vector2 pos;
		Vector2 myPos = e.getPosition();
		float dis = Float.MAX_VALUE;
		for (Entity entity : mobs) {
			if (entity.getUnitType() != t)
				continue;
			pos = entity.getPosition();
			float myDis = distance(myPos, pos);
			if (myDis < dis) {
				closest = entity;
				dis = myDis;
			}
		}

		return closest;
	}

	public LinkedList<Entity> getEntities() {
		return entityList;
	}

	public void update(float deltaTime) {
		for (Entity entity : entityList) {
			entityPositionSet.remove(entity.position);
			entity.update(deltaTime);
			entityPositionSet.add(entity.position);
		}
		
		for (SpawnTile spawn : spawnTileList) {
			spawn.tickSpawn();
		}
	}

	public float distance(Vector2 a, Vector2 b) {
		return (float) Math.sqrt(Math.pow(b.x - a.x, 2)
				+ Math.pow(b.y - a.y, 2));
	}

	public Entity getClosest(Entity e, Faction f) {
		Entity closest = null;
		LinkedList<Entity> mobs = getFactionMembers(f);

		Vector2 pos;
		Vector2 myPos = e.getPosition();
		float dis = 9999f;
		for (Entity entity : mobs) {
			pos = entity.getPosition();
			float myDis = distance(myPos, pos);
			if (myDis < dis) {
				closest = entity;
				dis = myDis;
			}
		}

		return closest;
	}

	public void queueRemoveEntity(Entity entity) {
		toRemoveList.add(entity);
	}

	public Entity addWithin(EntityType type, Vector2 loc, Vector2 max, int dist) {
		Entity entity = getEntity(type);

		Vector2 pos = loc.cpy();
		pos.x += MathUtils.random(0, dist);
		pos.x -= MathUtils.random(0, dist);
		pos.y += MathUtils.random(0, dist);
		pos.y -= MathUtils.random(0, dist);

		GameTools.clamp(pos, new Vector2(0, 0), max);

		entity.position = pos.cpy();
		addEntity(entity);
		return entity;
	}

	public Entity getEntity(EntityType type) {
		switch (type) {
		case Monster:
			return new Monster(new Vector2(0, 0), Facing.getRandom());
		case Villager:
			return new Villager(new Vector2(0, 0), Facing.getRandom());
		case Soldier:
			return new Soldier(new Vector2(0, 0), Facing.getRandom());
		case Mage:
			return new Mage(new Vector2(0, 0), Facing.getRandom());
		case Archer:
			return new Archer(new Vector2(0, 0), Facing.getRandom());

		default:
			throw new GameException("Entity type not recognised!");
		}
	}

	public boolean isEntityAt(Vector2 position) {
		return entityPositionSet.contains(position);
	}

}
