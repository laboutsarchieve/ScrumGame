package data;

public class Player {
	float mana;
	float MaxMana;
	float manaPercent;
	float cooldownGodAttackMax=300; //30 fps so a full charge every 100 seconds (needs fine tuning for a sweet spot)
	float cooldownGodAttack=0;
	float cooldownGodAttackPercent=1;
	
	public Player()
	{
		this.mana=100;
		this.MaxMana=100;
		setManaPercent();
	}
	
	public Player(float mana, float MaxMana)
	{
		this.mana=mana;
		this.MaxMana=MaxMana;
		
		setManaPercent();
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
		setManaPercent();
	}
	public void addMana(float mana)
	{
		mana+=this.mana;
		if(mana>MaxMana)
		{
			mana=MaxMana;
		}
		this.mana=mana;
		setManaPercent();
	}
	public void subMana(float mana)
	{
		this.mana-=mana;
		if(this.mana<0){
			this.mana=0;
		}
		setManaPercent();
	}
	
	public void setMaxMana(float mana)
	{
		this.MaxMana=mana;
		setManaPercent();
	}
	
	private void setManaPercent()
	{
		this.manaPercent=this.mana/this.MaxMana;
		this.cooldownGodAttackPercent=(this.cooldownGodAttackMax-this.cooldownGodAttack)/this.cooldownGodAttackMax;
	}
	public void addGodCharge(float amount){
		amount+=this.cooldownGodAttack;
		if(amount>this.cooldownGodAttackMax){
			amount=this.cooldownGodAttackMax;
		}
		this.cooldownGodAttack=amount;
		setManaPercent();
	}
	
	public float getManaPercent()
	{
		return manaPercent;
	}
	
	public float getGodAttackPercent()
	{
		return this.cooldownGodAttackPercent;
	}
}
