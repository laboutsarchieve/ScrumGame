package data;

import view.AnimatedSprite;
import view.SheetType;

import application.MainGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Archer extends Entity {

	public Archer(Vector2 position, Facing facing) {
		super(position, facing, Faction.Player);
		animations = new AnimatedSprite(MainGame.getTextureRepo()
				.getSpriteSheet(SheetType.Archer));
		
		unitType = EntityType.Archer;
		init();
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

	public Sprite getSprite() {
		return animations.getSprite();
	}
}
