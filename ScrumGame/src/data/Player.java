package data;

public class Player {
	int mana;
	int MaxMana;
	
	public Player()
	{
		this.mana=100;
		this.MaxMana=100;
	}
	
	public Player(int mana, int MaxMana)
	{
		this.mana=mana;
		this.MaxMana=MaxMana;
	}
	
	public int getMana() {
		return mana;
	}

	public int getMaxMana()
	{
		return MaxMana;
	}
	public void setMana(int mana) {
		this.mana = mana;
	}
	public void addMana(int mana)
	{
		this.mana+=mana;
	}
	public void subMana(int mana)
	{
		this.mana -=mana;
	}
	
	public void setMaxMana(int mana)
	{
		this.MaxMana=mana;
	}
}
