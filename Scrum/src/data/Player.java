package data;

public class Player {
	float mana;
	float MaxMana;
	
	public Player()
	{
		this.mana=100;
		this.MaxMana=100;
	}
	
	public Player(float mana, float MaxMana)
	{
		this.mana=mana;
		this.MaxMana=MaxMana;
	}
	
	public float getMana() {
		return mana;
	}

	public float getMaxMana()
	{
		return MaxMana;
	}
	public void setMana(float mana) {
		this.mana = mana;
	}
	public void addMana(float mana)
	{
		this.mana+=mana;
		if(this.mana>MaxMana)
		{
			this.mana=MaxMana;
		}
	}
	public void subMana(float mana)
	{
		this.mana -=mana;
	}
	
	public void setMaxMana(float mana)
	{
		this.MaxMana=mana;
	}
}
