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
			break;
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
			break;
		case Attack:
			break;
		case Dead:
			default:
				//this shouldn't happen, so just remove the unit
				MainGame.getEntityManager().removeEntity(this);
		}
	}
}
