package Data;

import java.util.HashMap;
import java.util.LinkedList;

public class EntityManager {
	HashMap<Faction, LinkedList<Entity>> factionLists = new HashMap<Faction, LinkedList<Entity>>( );  
	LinkedList<Entity> entityList = new LinkedList<Entity>( );
	
	public EntityManager( ){
		factionLists.put(Faction.Player, new LinkedList<Entity>( ));
		factionLists.put(Faction.Villager, new LinkedList<Entity>( ));
		factionLists.put(Faction.Monster, new LinkedList<Entity>( ));				
	}
	
	public void addEntity(Entity toAdd) {
		entityList.add(toAdd);
		factionLists.get(toAdd.getFaction( )).add(toAdd);
	}
	public void removeEntity(Entity toAdd) {
		entityList.remove(toAdd);
		factionLists.get(toAdd.getFaction( )).remove(toAdd);
	}
	public LinkedList<Entity> getFactionMembers(Faction faction) {
		return factionLists.get(faction);
	}
	public LinkedList<Entity> getEntities() {
		return entityList;
	}

	public void update(float deltaTime) {
		for(Entity entity : entityList) {
			entity.update(deltaTime);
		}
	}

}
