package data;

import view.AnimatedSprite;
import view.SheetType;
import application.MainGame;

import com.badlogic.gdx.math.Vector2;

public class Villager extends Entity {
	
	private Entity soldierBuddy;
	
	public Villager(Vector2 position, Facing facing) {
		super(position, facing, Faction.Villager);
		animations = new AnimatedSprite(MainGame.getTextureRepo().getSpriteSheet(SheetType.Villager));
	
		unitType = EntityType.Villager;
		init();
		
		myVillagerID = globalVillagerID;
		globalVillagerID++;
	}
	
	@Override
	protected void takeAction() {
		switch(state) {
		case Idle:
			state = AIState.Roam;
		case Roam:
			actionInterval = GameData.getActionInterval(unitType);
			if (validTarget())
				state = AIState.Flee;
			else
				roam();
			break;
		case Flee:
			actionInterval = GameData.getAggroInterval(unitType);
			soldierBuddy = manager.getClosestType(this, EntityType.Soldier, Faction.Player);
			if (validTarget(soldierBuddy))
				moveTo(soldierBuddy);
			else
				roam();
			break;
		default:
			//this shouldn't happen, so just kill the unit
			state = AIState.Dead;
		}
	}
	
	protected  void attackedByEntity(Entity e) {
		if (target == null)
			target = e;
	}
}
