package testgame.battle.monsters;

import java.awt.event.MouseEvent;

import testgame.Handler;
import testgame.battle.Battle;
import testgame.gfx.Animation;
import testgame.ui.UIObject;

public abstract class Monster extends Battle
{
	protected int health;		//character health
	protected int currHP;
	protected int strength;		//character inner strength			!!
	protected int weaponA;		//characters weapon strength
	protected int attack;		//characters total attack power
	protected int resilience;	//characters resistance to damage	!!
	protected int armor;		//characters defense do to armor
	protected int defense;		//total defense
	protected int intelligence;	//magic damage						!!
	protected int weaponM;		//magic damage from weapon
	protected int attackM;		//total magic damage
	protected int magic;		//total MP used for skills and magic
	protected int currMP;
	protected int speed;		//character's speed					!!
	protected int exp;			//total experience
	protected int xpDrop;		//exp dropped from enemies
	protected int level;		//players current level				!!
	protected int gold;
	protected int Gdrop;
	
	protected String name;
	
	public int damage;
	public boolean poisoned;
	public boolean paralyze;
	public boolean doNothing;
	
	public boolean poisonedStatus;
	public boolean paralyzeStatus;
	
	String dialog, dialog2;
	protected int target;
	protected Animation animation;
	
	public static final int default_creature_width = 64, default_creature_height = 64;
	
	public static final int default_health = 1, default_strength = 0, defaut_weaponA = 0, default_attack = 0;
	public static final int default_res = 0, default_armor = 0, default_def = 0, default_speed = 0;
	public static final int default_int = 0, default_weaponM = 0, default_attackM = 0, default_mag = 0;
	public static final int default_exp = 0, default_drop = 1, default_level = 1;
	public static final String default_name = "Dummy";
	public static final int default_gold = 0, default_Gdrop = 10;
	
	public Monster(Handler handler, float x, float y, int width, int height)
	{
		super(handler, x, y, width, height);
		
		health = default_health;
		strength = default_strength;
		weaponA = defaut_weaponA;
		attack = default_attack;
		resilience = default_res;
		armor = default_armor;
		defense = default_def;
		intelligence = default_int;
		weaponM = default_weaponM;
		attackM = default_attackM;
		magic = default_mag;
		speed = default_speed;
		exp = default_exp;
		xpDrop = default_drop;
		level = default_level;
		name = default_name;
		
		currHP = health;
		currMP = magic;
		
		gold = default_gold;
		Gdrop = default_Gdrop;
	}

	public abstract void battle();
	
	//Getters and Setters
	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public int getStrength()
	{
		return strength;
	}

	public void setStrength(int strength)
	{
		this.strength = strength;
	}

	public int getWeaponA()
	{
		return weaponA;
	}

	public void setWeaponA(int weaponA)
	{
		this.weaponA = weaponA;
	}

	public int getAttack()
	{
		return attack;
	}

	public void setAttack(int attack)
	{
		this.attack = attack;
	}

	public int getResilience()
	{
		return resilience;
	}

	public void setResilience(int resilience)
	{
		this.resilience = resilience;
	}

	public int getArmor()
	{
		return armor;
	}

	public void setArmor(int armor)
	{
		this.armor = armor;
	}

	public int getDefense()
	{
		return defense;
	}

	public void setDefense(int defense)
	{
		this.defense = defense;
	}

	public int getIntelligence()
	{
		return intelligence;
	}

	public void setIntelligence(int intelligence)
	{
		this.intelligence = intelligence;
	}

	public int getWeaponM()
	{
		return weaponM;
	}

	public void setWeaponM(int weaponM)
	{
		this.weaponM = weaponM;
	}

	public int getAttackM()
	{
		return attackM;
	}

	public void setAttackM(int attackM)
	{
		this.attackM = attackM;
	}

	public int getMagic()
	{
		return magic;
	}

	public void setMagic(int magic)
	{
		this.magic = magic;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public int getExp()
	{
		return exp;
	}

	public void setExp(int exp)
	{
		this.exp = exp;
	}

	public int getXpDrop()
	{
		return xpDrop;
	}

	public void setXpDrop(int xpDrop)
	{
		this.xpDrop = xpDrop;
	}


	public int getLevel()
	{
		return level;
	}


	public void setLevel(int level)
	{
		this.level = level;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public int getCurrHP()
	{
		return currHP;
	}


	public void setCurrHP(int currHP)
	{
		this.currHP = currHP;
	}


	public int getCurrMP()
	{
		return currMP;
	}


	public void setCurrMP(int currMP)
	{
		this.currMP = currMP;
	}


	public int getGold()
	{
		return gold;
	}


	public void setGold(int gold)
	{
		this.gold = gold;
	}


	public int getGdrop()
	{
		return Gdrop;
	}


	public void setGdrop(int gdrop)
	{
		Gdrop = gdrop;
	}

	public int getDamage()
	{
		return damage;
	}

	public void setDamage(int damage)
	{
		this.damage = damage;
	}

	public boolean isPoisoned()
	{
		return poisoned;
	}

	public void setPoisoned(boolean poisoned)
	{
		this.poisoned = poisoned;
	}

	public boolean isParalyze()
	{
		return paralyze;
	}

	public void setParalyze(boolean paralyze)
	{
		this.paralyze = paralyze;
	}

	public String getDialog()
	{
		return dialog;
	}

	public void setDialog(String dialog)
	{
		this.dialog = dialog;
	}

	public String getDialog2()
	{
		return dialog2;
	}

	public void setDialog2(String dialog2)
	{
		this.dialog2 = dialog2;
	}

	public boolean isPoisonedStatus()
	{
		return poisonedStatus;
	}

	public void setPoisonedStatus(boolean poisonedStatus)
	{
		this.poisonedStatus = poisonedStatus;
	}

	public boolean isParalyzeStatus()
	{
		return paralyzeStatus;
	}

	public void setParalyzeStatus(boolean paralyzeStatus)
	{
		this.paralyzeStatus = paralyzeStatus;
	}

	public boolean isDoNothing()
	{
		return doNothing;
	}

	public void setDoNothing(boolean doNothing)
	{
		this.doNothing = doNothing;
	}

	public int getTarget()
	{
		return target;
	}

	public void setTarget(int target)
	{
		this.target = target;
	}

	public Animation getAnimation()
	{
		return animation;
	}

	public void setAnimation(Animation animation) 
	{
		this.animation = animation;
	}
	

}
