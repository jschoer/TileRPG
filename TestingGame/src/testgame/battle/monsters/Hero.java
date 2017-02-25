package testgame.battle.monsters;

import java.awt.Graphics;

import testgame.Handler;
import testgame.gfx.Animation;
import testgame.gfx.Assests;

public class Hero extends Monster
{

	public Hero(Handler handler, float x, float y, int width, int height)
	{
		super(handler, x, y, width, height);
		setHealth(25);
		setCurrHP(getHealth());
		setStrength(5);
		setResilience(1);
		setIntelligence(0);
		setSpeed(3);
		setAttack(getStrength() + getWeaponA());
		setDefense(getResilience() + getArmor());
		setName(handler.getYourName());
	}
	
	public void battle()
	{
		setAttack(getStrength() + getWeaponA());
		setDefense(getResilience() + getArmor());
	}

	@Override
	public void tick()
	{
		setAttack(getStrength() + getWeaponA());
		setDefense(getResilience() + getArmor());
		setName(handler.getYourName());
	}

	@Override
	public void render(Graphics g){}

	@Override
	public void onClick()
	{
		// TODO Auto-generated method stub
		
	}
	
}
