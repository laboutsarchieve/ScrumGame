package data;

import view.AnimatedSprite;
import view.SheetType;

import application.MainGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Mage extends Entity {
	
	public Mage(Vector2 position, Facing facing) {
		super(position, facing, Faction.Player);
		animations = new AnimatedSprite(MainGame.getTextureRepo().getSpriteSheet(SheetType.Mage));
	
		unitType = EntityType.Mage;
		init();
	}
	
	@Override
	protected void takeAction() {
		roam();
	}
}
