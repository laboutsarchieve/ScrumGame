package data;

public enum AIState {
	Idle,
	Roam,
	Hunt,
	Attack,
	Flee,
	Dead,
	Disabled
}

/**
 * AI Summary
 * 
 * General:
 * 1. All units move/act faster when not in roam state
 * 
 * Monster
 * 1. find closest villager, begin hunt if in range
 * 	1a. if no villagers left, hunt player units
 * 2. Attack target until dead
 * 3. If attacked by player unit while villager is target, switch hunt target to player unit
 * 
 * Soldier
 * 1. Wander around the map, attack nearby monsters
 * 
 * Archer/Mage
 * 1. Head towards nearest soldier, when within a few tiles can freely roam around soldier
 * 2. When monster spotted, attack monster until dead
 * 
 * Villager
 * 1. Wander around map randomly
 * 2. If attacked, run towards nearest soldier
 */
