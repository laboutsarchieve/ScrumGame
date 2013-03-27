package data;

import view.AnimatedSprite;
import view.SheetType;

import application.MainGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Archer extends Entity {

	private Entity soldierBuddy;
	
	public Archer(Vector2 position, Facing facing) {
		super(position, facing, Faction.Player);
		animations = new AnimatedSprite(MainGame.getTextureRepo()
				.getSpriteSheet(SheetType.Archer));
		
		unitType = EntityType.Archer;
		init();
	}
	
	@Override
	protected void takeAction() {
		switch(state) {
		case Idle:
			state = AIState.Roam;
		case Roam:
			actionInterval = GameData.getActionInterval(unitType);
			if (!validTarget(soldierBuddy))
					soldierBuddy = manager.getClosestType(this, EntityType.Soldier, Faction.Player);
				
			target = manager.getClosest(this, Faction.Monster);
			
			if (target != null) {
				if (target.getState() == AIState.Dead || target.getState() == AIState.Disabled) {
					target = null;
					break;
				}
				
				targetRange = manager.distance(target.getPosition(), position);
				if ( targetRange <= visionRange )
					state = AIState.Hunt;
			}
			
			if (state == AIState.Roam) {
				if (manager.distance(soldierBuddy.getPosition(), position) <= 2)
					roam();
				else
					moveTo(soldierBuddy);
			}
				
			break;
		case Hunt:
			actionInterval = GameData.getAggroInterval(unitType);
			if (!validTarget()) {
				state = AIState.Roam;
				break;
			}
			moveTo(target);
			targetRange = manager.distance(target.getPosition(), position);
			
			if (targetRange <= attackRange)
				state = AIState.Attack;
			
			if (targetRange > visionRange + 3) {
				state = AIState.Roam;
			}
			
			break;
		case Attack:
			if (!attack(target)) {
				state = AIState.Hunt;
			}
			
			if (!validTarget())
			{
				target = null;
				state = AIState.Roam;
			}
			break;
		default:
		}
	}
	
	protected  void attackedByEntity(Entity e) {
		//null
	}
}
