package data;

import view.AnimatedSprite;

import application.MainGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	protected float tillNextMove = 0;
	//TODO: This should managed in a separate but parallel class
	protected AnimatedSprite animations;
	
	protected Vector2 position;
	protected Facing facing;
	Faction faction;
	
	protected EntityType unitType;
	protected int hitpoints;
	protected int attackDamage;
	protected int attackRange;
	protected int visionRange;
	protected int aggroRange;
	protected float actionInterval;
	protected float aggroActionInterval;
	protected AIState state;
	
	public Entity(Vector2 startPosition, Facing startFacing, Faction faction) {
		this.position = startPosition;
		this.facing = startFacing;
		this.faction = faction;
		state = AIState.Idle;
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
	
	public int getAggroRange() {
		return aggroRange;
	}
	
	protected void init() {
		hitpoints = GameData.getHitpoints(unitType);
		attackDamage = GameData.getAttackDamage(unitType);
		attackRange = GameData.getRange(unitType);
		visionRange = GameData.getVisionRadius(unitType);
		aggroRange = GameData.getAggroRadius(unitType);
		actionInterval = GameData.getActionInterval(unitType);
		aggroActionInterval = GameData.getAggroInterval(unitType);
	}
	
	protected void move() {
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
}
