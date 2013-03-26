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
			target = manager.getClosest(this, Faction.Villager);
			if (manager.distance(target.getPosition(), position) < visionRange) {
				state = AIState.Hunt;
			} else {
				roam();
			}	
			break;
		case Hunt:
			moveTo(target);
			state = AIState.Attack;
			break;
		case Attack:
			if (!attack(target)) {
				state = AIState.Hunt;
			}
			
			//attack() will set target to null if target dies
			if (target == null)
			{
				state = AIState.Roam;
			}
			break;
		case Dead:
			break;
			default:
				//this shouldn't happen, so just remove the unit
				MainGame.getEntityManager().removeEntity(this);
		}
	}
}
