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
			if (!validTarget(soldierBuddy))
					soldierBuddy = manager.getClosestType(this, EntityType.Soldier, Faction.Player);
				
		case Hunt:
		case Attack:
		default:
		}
		
		
		
		
		
		
		target = manager.getClosestType(this, EntityType.Soldier, Faction.Player);
		moveTo(target);
		roam();
	}
	
	protected  void attackedByEntity(Entity e) {
		//null
	}
}
