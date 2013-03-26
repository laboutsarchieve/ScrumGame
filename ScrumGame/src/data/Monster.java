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
	
	@Override
	protected void takeAction() {
		switch(state) {
		case Idle:
			state = AIState.Roam;
		case Roam:
			if (!validTarget())
				target = manager.getClosest(this, Faction.Villager);
			
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
