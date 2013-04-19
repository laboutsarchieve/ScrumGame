package application;

public class LevelData {
	private static int monstersTillNextLevel;
	private static int villagersTillGameOver;
	
	public static void monsterDied( ) {
		monstersTillNextLevel--;
	}

	public static void villagerDied( ) {
		monstersTillNextLevel--;
	}
	
	public static int getMonstersTillNextLevel() {
		return monstersTillNextLevel;
	}

	public static int getVillagersTillGameOver() {
		return villagersTillGameOver;
	}
}
