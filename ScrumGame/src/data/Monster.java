package data;

import view.AnimatedSprite;
import view.SheetType;

import application.MainGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Monster extends Entity {

	
	public Monster(Vector2 position, Facing facing) {
		super(position, facing, Faction.Monster);
		animations = new AnimatedSprite(MainGame.getTextureRepo().getSpriteSheet(SheetType.Monster));
	
		unitType = EntityType.Monster;
		init();
	}
	
	/**
	 * Monster AI summary:
	 * 1. find closest villager, begin hunt if in range
	 * 	1a. if no villagers left, hunt player units
	 * 2. if in range, begin moving faster for the hunt
	 * 3. attack target until dead
	 * 4. start from 1
	 * 
	 * If attacked by player unit while villager is target, switch hunt target to player unit
	 */
	
	@Override
	protected void takeAction() {
		switch(state) {
		case Idle:
			state = AIState.Roam;
		case Roam:
			if (!validTarget())
				if((target = manager.getClosest(this, Faction.Villager)) == null)
					target = manager.getClosest(this, Faction.Player);
			
			if (target != null) {
				if (target.getState() == AIState.Dead || target.getState() == AIState.Disabled) {
					target = null;
					break;
				}
				
				targetRange = manager.distance(target.getPosition(), position);
				if ( targetRange < visionRange )
					state = AIState.Hunt;
			} 
			
			roam();
			break;
		case Hunt:
			if (!validTarget()) {
				state = AIState.Roam;
				actionInterval = GameData.getActionInterval(unitType);
				break;
			}
			actionInterval = GameData.getAggroInterval(unitType);
			moveTo(target);
			targetRange = manager.distance(target.getPosition(), position);
			
			if (targetRange <= attackRange)
				state = AIState.Attack;
			break;
		case Attack:
			if (!attack(target)) {
				state = AIState.Hunt;
			}
			
			if (!validTarget())
			{
				target = null;
				state = AIState.Roam;
				actionInterval = GameData.getActionInterval(unitType);
			}
			break;
		case Dead:
			//handled in parent class
			break;
		default:
			//this shouldn't happen, so just kill the unit
			state = AIState.Dead;
		}
	}
	
	/**
	 * If monster is hunting a villager, being attacked by player unit will switch target
	 * to that unit
	 */
	protected  void attackedByEntity(Entity e) {
		if (target == null)
			target = e;
		else {
			if (target.getFaction() != Faction.Player && e.getFaction() == Faction.Player)
				target = e;
		}
	}
}
