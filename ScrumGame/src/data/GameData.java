package data;

import application.LevelData;
import java.io.*;
import java.util.Scanner;

public class GameData {
	private static int baseMonsterHP = 100;
	private static int baseSoldierHP = 200;
	private static int baseArcherHP = 80;
	private static int baseMageHP = 70;
	private static int baseVillagerHP = 40;

	private static int baseMonsterATK = 20;
	private static int baseSoldierATK = 10;
	private static int baseArcherATK = 20;
	private static int baseMageATK = 30;
	private static int baseVillagerATK = 0;

	// Attack range
	private static int baseMonsterRange = 1;
	private static int baseSoldierRange = 1;
	private static int baseArcherRange = 6;
	private static int baseMageRange = 4;
	private static int baseVillagerRange = 0;

	// This is the scout/explore radius to begin moving towards a unit
	private static int baseMonsterVision = 6;
	private static int baseSoldierVision = 7;
	private static int baseArcherVision = 8;
	private static int baseMageVision = 8;
	private static int baseVillagerVision = 7;

	// This is the radius to begin offensive/defensive action
	private static int baseMonsterAggro = 7;
	private static int baseSoldierAggro = 8;
	private static int baseArcherAggro = 9;
	private static int baseMageAggro = 9;
	private static int baseVillagerAggro = 8;

	private static float baseMonsterActionInterval = 1.5f;
	private static float baseSoldierActionInterval = 1.5f;
	private static float baseArcherActionInterval = 1.2f;
	private static float baseMageActionInterval = 1.3f;
	private static float baseVillagerActionInterval = 2.0f;

	private static float baseMonsterAggroInterval = 0.9f;
	private static float baseSoldierAggroInterval = 1.0f;
	private static float baseArcherAggroInterval = 0.8f;
	private static float baseMageAggroInterval = 0.8f;
	private static float baseVillagerAggroInterval = 1.3f;

	// Note: Only affects new units created after being modified
	private static float difficultyMultiplier = 1.0f;
	// These are dynamic difficulty scaling, this is a mult to attack power
	private static float difficultyPlayerDamageMult = 1.0f;
	private static float difficultyMonsterDamageMult = 1.0f;

	/**
	 * A function to read data from a file could be added here
	 */

	public static void setDifficultyMultiplier(float d) {
		difficultyMultiplier = d;
	}

	public static void setPlayerDamageMult(float d) {
		difficultyPlayerDamageMult = d;
	}
	
	public static void setMonsterDamageMult(float d) {
		difficultyMonsterDamageMult = d;
	}
	
	public static float getDamageMult(Faction f) {
		float mult = 1;
		switch(f){
		case Monster:
			mult = difficultyMonsterDamageMult;
			break;
		case Player:
			mult = difficultyPlayerDamageMult;
			break;
		default:
			mult = 1;
			break;
		}
		return mult;
	}
	
	public static int getHitpoints(EntityType t) {
		int hp = 0;
		switch (t) {
		case Monster:
			hp = (int) (baseMonsterHP * difficultyMultiplier);
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
		switch (t) {
		case Monster:
			atk = (int) (baseMonsterATK * difficultyMultiplier);
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
		switch (t) {
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
		switch (t) {
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

	public static int getAggroRadius(EntityType t) {
		int v = 0;
		switch (t) {
		case Monster:
			v = baseMonsterAggro;
			break;
		case Soldier:
			v = baseSoldierAggro;
			break;
		case Archer:
			v = baseArcherAggro;
			break;
		case Mage:
			v = baseMageAggro;
			break;
		case Villager:
			v = baseVillagerAggro;
			break;
		default:
			v = 0;
		}
		return v;
	}

	public static float getActionInterval(EntityType t) {
		float m = 0;
		switch (t) {
		case Monster:
			m = Math.max(0.5f, baseMonsterActionInterval - 0.05f * LevelData.getLevel());
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

	public static float getAggroInterval(EntityType t) {
		float m = 0;
		switch (t) {
		case Monster:
			m = baseMonsterAggroInterval / difficultyMultiplier;
			break;
		case Soldier:
			m = baseSoldierAggroInterval;
			break;
		case Archer:
			m = baseArcherAggroInterval;
			break;
		case Mage:
			m = baseMageAggroInterval;
			break;
		case Villager:
			m = baseVillagerAggroInterval;
			break;
		default:
			m = 1.0f;
		}
		return m;
	}

	public static int getSpawnRate(EntityType t) {
		int m = 0;
		switch (t) {
		case Monster:
			m = Math.max(600 - 50 * LevelData.getLevel(), 50);
			break;
		case Villager:
			m = Math.max(600 - 50 * LevelData.getLevel(), 50);
			break;
		default:
			m = 1000;
		}
		return m;
	}
	
	public static boolean updateGameStats() {
		String file = "data.ini";
        Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader(file)));
            //This is pretty hard-coded in required file format
            //But it does leave room for comments in the file
            difficultyMultiplier = getNextFloat(s);
            difficultyPlayerDamageMult = getNextFloat(s);
            difficultyMonsterDamageMult = getNextFloat(s);
            
            baseMonsterHP = getNextInt(s);
            baseMonsterATK = getNextInt(s);
            baseMonsterRange = getNextInt(s);
            baseMonsterVision = getNextInt(s);
            baseMonsterAggro = getNextInt(s);
            baseMonsterActionInterval = getNextFloat(s);
            baseMonsterAggroInterval = getNextFloat(s);
            
            baseSoldierHP = getNextInt(s);
            baseSoldierATK = getNextInt(s);
            baseSoldierRange = getNextInt(s);
            baseSoldierVision = getNextInt(s);
            baseSoldierAggro = getNextInt(s);
            baseSoldierActionInterval = getNextFloat(s);
            baseSoldierAggroInterval = getNextFloat(s);
            
            baseArcherHP = getNextInt(s);
            baseArcherATK = getNextInt(s);
            baseArcherRange = getNextInt(s);
            baseArcherVision = getNextInt(s);
            baseArcherAggro = getNextInt(s);
            baseArcherActionInterval = getNextFloat(s);
            baseArcherAggroInterval = getNextFloat(s);
            
            baseMageHP = getNextInt(s);
            baseMageATK = getNextInt(s);
            baseMageRange = getNextInt(s);
            baseMageVision = getNextInt(s);
            baseMageAggro = getNextInt(s);
            baseMageActionInterval = getNextFloat(s);
            baseMageAggroInterval = getNextFloat(s);
            
            baseVillagerHP = getNextInt(s);
            baseVillagerATK = getNextInt(s);
            baseVillagerRange = getNextInt(s);
            baseVillagerVision = getNextInt(s);
            baseVillagerAggro = getNextInt(s);
            baseVillagerActionInterval = getNextFloat(s);
            baseVillagerAggroInterval = getNextFloat(s);
        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            if (s != null)
                s.close();
        }
		return true;
	}
	
	private static float getNextFloat(Scanner s) {
		while(!s.hasNextFloat()) {
			s.nextLine();
		}
		return s.nextFloat();
	}
	
	private static int getNextInt(Scanner s) {
		while(!s.hasNextInt()){
			s.nextLine();
		}
		return s.nextInt();
	}
}
