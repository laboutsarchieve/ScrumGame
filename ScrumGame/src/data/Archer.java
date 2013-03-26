package data;

import view.AnimatedSprite;
import view.SheetType;

import application.MainGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Archer extends Entity {
	private float tillNextMove = 0;
	// TODO: This should managed in a separate but parallel class
	private AnimatedSprite animations;

	public Archer(Vector2 position, Facing facing) {
		super(position, facing, Faction.Player);
		animations = new AnimatedSprite(MainGame.getTextureRepo()
				.getSpriteSheet(SheetType.Archer));
		
		unitType = EntityType.Archer;
		hitpoints = GameData.getHitpoints(unitType);
		attackDamage = GameData.getAttackDamage(unitType);
		attackRange = GameData.getRange(unitType);
		actionInterval = GameData.getActionInterval(unitType);
	}

	@Override
	public void update(float deltaTime) {
		animations.update(deltaTime);
		tillNextMove -= deltaTime;

		while (tillNextMove < 0) {
			move();
			tillNextMove += actionInterval;
		}
	}

	private void move() {
		Facing nextFacing = facing;
		if (MathUtils.random() > 0.6) {
			nextFacing = Facing.getRandom();
		}

		Vector2 oldPosition = position.cpy();
		switch (nextFacing) {
		case Down:
			position.y--;
			animations.setCurrAnimation(0);
			break;
		case Left:
			position.x--;
			animations.setCurrAnimation(1);
			break;
		case Right:
			position.x++;
			animations.setCurrAnimation(2);
			break;
		case Up:
			position.y++;
			animations.setCurrAnimation(3);
			break;
		default:
			break;
		}

		HeightMap map = MainGame.getMap();

		if (!map.contains(position)
				|| map.getTileType(position) != TileType.Grass) {
			position = oldPosition;
			facing = Facing.getRandom();
		}
	}

	public Sprite getSprite() {
		return animations.getSprite();
	}
}
