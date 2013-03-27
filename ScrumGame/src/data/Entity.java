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
	protected EntityManager manager;
	
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
	protected AIState state;
	protected Entity target = null;
	protected float targetRange;
	
	private static int deathcount = 0;
	
	public Entity(Vector2 startPosition, Facing startFacing, Faction faction) {
		this.position = startPosition;
		this.facing = startFacing;
		this.faction = faction;
		state = AIState.Idle;
		manager = MainGame.getEntityManager();
	}
	
	public void update(float deltaTime) {
		if(state == AIState.Dead) {
			death();
		}
		
		if (state != AIState.Disabled){
			
			animations.update(deltaTime);
			tillNextMove -= deltaTime;
	
			while (tillNextMove < 0) {
				takeAction();
				tillNextMove += actionInterval;
			}
		}
	}
	
	public Sprite getSprite() {
		return animations.getSprite();
	}
	
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
	
	public AIState getState() {
		return state;
	}
	
	protected void init() {
		hitpoints = GameData.getHitpoints(unitType);
		attackDamage = GameData.getAttackDamage(unitType);
		attackRange = GameData.getRange(unitType);
		visionRange = GameData.getVisionRadius(unitType);
		aggroRange = GameData.getAggroRadius(unitType);
		actionInterval = GameData.getActionInterval(unitType);
	}
	
	protected abstract void takeAction();
	
	protected void roam() {
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

	protected boolean attack(Entity e){
		boolean attackSuccess = false;
		if ( manager.distance(e.getPosition(), position) <= attackRange) {
			e.takeDamage(this);
			attackSuccess = true;
		}
		
		if(e.getState() == AIState.Dead || e.getState() == AIState.Disabled)
			target = null;
		
		return attackSuccess;
	}
	
	protected void takeDamage(Entity e) {
		hitpoints -= e.getAttackDamage();
		
		attackedByEntity(e);
		
		if (hitpoints <= 0) {
			state = AIState.Dead;
		}
	}
	
	protected abstract void attackedByEntity(Entity e);

	protected void death() {
		state = AIState.Disabled;
		manager.queueRemoveEntity(this);
		
		deathcount++;
		System.out.println("omg im dead " + deathcount);
	}
	//test func
	protected void moveTo(Entity e) {
		Vector2 targetPos = e.getPosition();
		Vector2 toTarget = targetPos.cpy().sub(position);
		if(toTarget.x != 0)
			toTarget.x /= Math.abs(toTarget.x);
		if(toTarget.y != 0)
			toTarget.y /= Math.abs(toTarget.y);
		
		Vector2 oldPosition = position.cpy( );
		
		position.add(toTarget);
		
		if(MainGame.getMap().getTileType(position) != TileType.Grass) {
			position = oldPosition.cpy( );
		}
		else {		
			if(toTarget.x == 1) {
				facing = Facing.Right;
			}
			else if(toTarget.x == -1) {
				facing = Facing.Left;
			}
			else if(toTarget.y == 1 ) {
				facing = Facing.Up;
			}
			else {
				facing = Facing.Down;
			}
				
		}
	}
}
