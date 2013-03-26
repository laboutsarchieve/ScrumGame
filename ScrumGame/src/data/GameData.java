package data;

public class GameData {
	private static int baseMonsterHP = 100;
	private static int baseSoldierHP = 100;
	private static int baseArcherHP = 80;
	private static int baseMageHP = 70;
	private static int baseVillagerHP = 50;
	
	private static int baseMonsterATK = 20;
	private static int baseSoldierATK = 10;
	private static int baseArcherATK = 15;
	private static int baseMageATK = 20;
	private static int baseVillagerATK = 1;
	
	private static int baseMonsterRange = 1;
	private static int baseSoldierRange = 1;
	private static int baseArcherRange = 4;
	private static int baseMageRange = 2;
	private static int baseVillagerRange = 1;
	
	private static int baseMonsterVision = 5;
	private static int baseSoldierVision = 5;
	private static int baseArcherVision = 6;
	private static int baseMageVision = 5;
	private static int baseVillagerVision = 6;
	
	private static float baseMonsterActionInterval = 0.9f;
	private static float baseSoldierActionInterval = 1.0f;
	private static float baseArcherActionInterval = 0.8f;
	private static float baseMageActionInterval = 0.9f;
	private static float baseVillagerActionInterval = 1.1f;
	
	private static float difficultyMultiplier = 1.0f;
	
	/**
	 * A function to read data from a file could be added here
	 */
	
	public static void setDifficultyMultiplier(float d) {
		difficultyMultiplier = d;
	}
	
	public static int getHitpoints(EntityType t) {
		int hp = 0;
		switch(t){
		case Monster:
			hp = (int)(baseMonsterHP * difficultyMultiplier);
			break;
		case Soldier:
			hp = baseSoldierHP;
			break;
		case Archer:
			hp = baseArcherHP;
			break;
		case Mage:
			hp = baseMageHP;
			break;
		case Villager:
			hp = baseVillagerHP;
			break;
		default:
			hp = -1;
		}
		return hp;
	}
	
	public static int getAttackDamage(EntityType t) {
		int atk = 0;
		switch(t){
		case Monster:
			atk = (int)(baseMonsterATK * difficultyMultiplier);
			break;
		case Soldier:
			atk = baseSoldierATK;
			break;
		case Archer:
			atk = baseArcherATK;
			break;
		case Mage:
			atk = baseMageATK;
			break;
		case Villager:
			atk = baseVillagerATK;
			break;
		default:
			atk = 0;
		}
		return atk;
	}
	
	public static int getRange(EntityType t) {
		int r = 0;
		switch(t){
		case Monster:
			r = baseMonsterRange;
			break;
		case Soldier:
			r = baseSoldierRange;
			break;
		case Archer:
			r = baseArcherRange;
			break;
		case Mage:
			r = baseMageRange;
			break;
		case Villager:
			r = baseVillagerRange;
			break;
		default:
			r = 0;
		}
		return r;
	}
	
	public static int getVisionRadius(EntityType t) {
		int v = 0;
		switch(t){
		case Monster:
			v = baseMonsterVision;
			break;
		case Soldier:
			v = baseSoldierVision;
			break;
		case Archer:
			v = baseArcherVision;
			break;
		case Mage:
			v = baseMageVision;
			break;
		case Villager:
			v = baseVillagerVision;
			break;
		default:
			v = 0;
		}
		return v;
	}
	
	public static float getActionInterval(EntityType t) {
		float m = 0;
		switch(t){
		case Monster:
			m = baseMonsterActionInterval / difficultyMultiplier;
			break;
		case Soldier:
			m = baseSoldierActionInterval;
			break;
		case Archer:
			m = baseArcherActionInterval;
			break;
		case Mage:
			m = baseMageActionInterval;
			break;
		case Villager:
			m = baseVillagerActionInterval;
			break;
		default:
			m = 1.0f;
		}
		return m;
	}
}
