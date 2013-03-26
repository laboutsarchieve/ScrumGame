package data;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	protected Vector2 position;
	protected Facing facing;
	Faction faction;
	
	protected EntityType unitType;
	protected int hitpoints;
	protected int attackDamage;
	protected int attackRange;
	protected int visionRange;
	protected float actionInterval;
	
	public Entity(Vector2 startPosition, Facing startFacing, Faction faction) {
		this.position = startPosition;
		this.facing = startFacing;
		this.faction = faction;
	}
	
	public abstract void update(float deltaTime);
	public abstract Sprite getSprite( );
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public Faction getFaction() {
		return faction;
	}
	
	public EntityType getUnitType() {
		return unitType;
	}
	
	public int getHitpoints() {
		return hitpoints;
	}
	
	public int getAttackDamage() {
		return attackDamage;
	}
	
	public int getAttackRange() {
		return attackRange;
	}
	
	public int getVisionRange() {
		return visionRange;
	}
	
	protected void init() {
		hitpoints = GameData.getHitpoints(unitType);
		attackDamage = GameData.getAttackDamage(unitType);
		attackRange = GameData.getRange(unitType);
		actionInterval = GameData.getActionInterval(unitType);
	}
}
