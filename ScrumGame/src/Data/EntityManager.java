package Data;

import java.util.LinkedList;

public class EntityManager {
	LinkedList<Entity> entityList = new LinkedList<Entity>( );
	
	public void addEntity(Entity toAdd) {
		entityList.add(toAdd);
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
