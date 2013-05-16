package data;

import view.AnimatedSprite;

import application.LevelData;
import application.MainGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	protected float tillNextMove = 0;
	// TODO: This should managed in a separate but parallel class
	protected AnimatedSprite animations;
	protected EntityManager manager;
	protected Vector2 position;
	protected Facing facing;
	protected boolean wasAttacked;
	protected Entity attackedRecently;
	protected int sinceAttacked;
	protected int sinceAttacking;
	Faction faction;

	protected EntityType unitType;
	protected int hitpoints;
	protected float healthScale;
	protected int attackDamage;
	protected int attackRange;
	protected int visionRange;
	protected int aggroRange;
	protected float actionInterval;
	protected AIState state;
	protected Entity target = null;
	protected float targetRange;

	private static int deathcount = 0;
	protected static int globalVillagerID = 1;
	protected int myVillagerID;

	public Entity(Vector2 startPosition, Facing startFacing, Faction faction) {
		this.position = startPosition;
		this.facing = startFacing;
		this.faction = faction;
		state = AIState.Idle;
		manager = MainGame.getEntityManager();
		attackRange = 1;
	}

	public void update(float deltaTime) {
		sinceAttacked++;
		sinceAttacking++;
		if (state == AIState.Dead) {
			death();
		}

		if (state != AIState.Disabled) {

			animations.update(deltaTime);
			tillNextMove -= deltaTime;

			while (tillNextMove < 0) {
				takeAction();
				tillNextMove += actionInterval;
			}
		}
	}

	public int getSinceAttacked() {
		return sinceAttacked;
	}

	public int getSinceAttacking() {
		return sinceAttacking;
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
		return (int)(attackDamage * GameData.getDamageMult(faction));
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
		setHealthScale();
		attackDamage = GameData.getAttackDamage(unitType);
		attackRange = GameData.getRange(unitType);
		visionRange = GameData.getVisionRadius(unitType);
		aggroRange = GameData.getAggroRadius(unitType);
		actionInterval = GameData.getActionInterval(unitType);
	}

	protected abstract void takeAction();

	protected boolean validTarget() {
		return !(target == null || (target.getState() == AIState.Dead || target
				.getState() == AIState.Disabled));
	}

	protected boolean validTarget(Entity t) {
		return !(t == null || (t.getState() == AIState.Dead || t.getState() == AIState.Disabled));
	}

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

		Map map = MainGame.getMap();

		if (!map.contains(position)
				|| map.getTileType(position) != TileType.Grass || MainGame.getEntityManager().isEntityAt(position)) {
			position = oldPosition;
			facing = Facing.getRandom();
		}
	}

	protected boolean attack(Entity e) {
		boolean attackSuccess = false;
		if (manager.distance(e.getPosition(), position) <= attackRange) {
			e.takeDamage(this);
			attackSuccess = true;
			attackedRecently = e;
			sinceAttacking = 0;
		}

		if (e.getState() == AIState.Dead || e.getState() == AIState.Disabled)
			target = null;

		return attackSuccess;
	}

	protected void takeDamage(Entity e) {
		wasAttacked = true;
		sinceAttacked = 0;
		hitpoints -= e.getAttackDamage();
		setHealthScale();
		attackedByEntity(e);

		if (hitpoints <= 0) {
			state = AIState.Dead;
		}
	}

	protected abstract void attackedByEntity(Entity e);

	protected void death() {
		if( this.getFaction() == Faction.Monster ) {
			LevelData.monsterDied();
		}
		else if( this.getFaction() == Faction.Villager ) {
			LevelData.villagerDied();
		}
		
		state = AIState.Disabled;
		manager.queueRemoveEntity(this);

		position.x = 9999;
		position.y = 9999;

		// debug output
		deathcount++;
		System.out.println("omg im dead " + deathcount + " " + myVillagerID);
	}

	protected void moveTo(Entity e, int howClose) {
		if(e == null)
			return;
		Vector2 targetPos = e.getPosition();
		Vector2 toTarget = targetPos.cpy().sub(position);
		float desiredDistance = (toTarget.len() - howClose);
		toTarget.nor().mul(desiredDistance);
		if (toTarget.x != 0)
			toTarget.x /= Math.abs(toTarget.x);
		if (toTarget.y != 0)
			toTarget.y /= Math.abs(toTarget.y);

		Vector2 oldPosition = position.cpy();

		position.add(toTarget);

		if (MainGame.getMap().getTileType(position) != TileType.Grass || MainGame.getEntityManager().isEntityAt(position)) {
			position = oldPosition.cpy();
			roam( );
		} else {
			if (toTarget.x == 1) {
				facing = Facing.Right;
			} else if (toTarget.x == -1) {
				facing = Facing.Left;
			} else if (toTarget.y == 1) {
				facing = Facing.Up;
			} else {
				facing = Facing.Down;
			}

			switch (facing) {
			case Down:

				animations.setCurrAnimation(0);
				break;
			case Left:

				animations.setCurrAnimation(1);
				break;
			case Right:

				animations.setCurrAnimation(2);
				break;
			case Up:

				animations.setCurrAnimation(3);
				break;
			default:
				break;
			}

		}
	}

	//Added By: Chris
	public float getHealthScale()
	{
		return healthScale;
	}
	private void setHealthScale()
	{
		healthScale = (float)this.getHitpoints()/(float)GameData.getHitpoints(this.getUnitType());
	}
	
	public boolean isWasAttacked() {
		return wasAttacked;
	}

	public void unsetWasAttacked() {
		wasAttacked = false;
	}
	
	public Entity attackedRecently() {
		return attackedRecently;
	}

	public void unsetAttackedRecently() {
		attackedRecently = null;
	}
}
