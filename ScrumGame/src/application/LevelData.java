package application;

public class LevelData {

	private static int level = 1;
	private static int monstersTillNextLevel = 10;
	private static int villagersTillGameOver = 20;
	private static int villagerCritical=10;
	private static int playerCharOnScreen=15;

	private static float monstersTillNextLevelPercent=0;
	
	public static int getLevel() {
		return level;
	}

	public static void levelUp() {
		LevelData.level++;
		playerCharOnScreen = 15 + LevelData.level * 6;
		LevelData.setMonstersTillNextLevel(LevelData.getLevel() * 10);
		LevelData.addVillagersTillGameOver(10 * LevelData.getLevel());
		setVillagerCriticalStatus();
	}

	public static void monsterDied( ) {
		int temp = monstersTillNextLevel;
		temp--;
		if(temp<0)
			temp=0;
		monstersTillNextLevel=temp;
		setPercentMonstersSlain();
	}

	public static void villagerDied( ) {
		int temp=villagersTillGameOver;
		temp--;
		if(temp<0)
			temp=0;
		villagersTillGameOver=temp;
	}
	
	public static int getMonstersTillNextLevel() {
		return monstersTillNextLevel;
	}

	public static void setMonstersTillNextLevel(int monstersTillNextLevel) {
		LevelData.monstersTillNextLevel = monstersTillNextLevel;
		setPercentMonstersSlain();
	}
	
	public static void addVillagersTillGameOver(int toAdd) {
		LevelData.villagersTillGameOver += toAdd;
	}
	
	public static void setVillagersTillGameOver(int villagersTillGameOver) {
		LevelData.villagersTillGameOver = villagersTillGameOver;
	}

	public static int getVillagersTillGameOver() {
		return villagersTillGameOver;
	}
	
	private static void setPercentMonstersSlain()
	{
		monstersTillNextLevelPercent =  ( (float)(level * 10) - (float)monstersTillNextLevel) / (float) (level * 10) ;
		System.out.println("Slain Percentage: "+monstersTillNextLevelPercent);
	}
	
	private static void setVillagerCriticalStatus()
	{
		int stat=villagersTillGameOver/4;
		if(stat < 10){stat=10;}
		villagerCritical=stat;
	}
	
	public static float getMonstersSlainPercent()
	{
		return monstersTillNextLevelPercent;
	}
	
	public static int getCriticalNumber()
	{
		return villagerCritical;
	}
	
	public static int getPlayerCharOnScreen() {
		return playerCharOnScreen;
	}
	public static void resetGame(){
		level = 1;
		monstersTillNextLevel = 10;
		villagersTillGameOver = 20;
		villagerCritical=10;
		playerCharOnScreen=15;
		monstersTillNextLevelPercent=0;
	}

}
