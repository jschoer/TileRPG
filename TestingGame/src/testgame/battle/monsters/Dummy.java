package testgame.battle.monsters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import testgame.Handler;
import testgame.gfx.Animation;
import testgame.gfx.Assests;
import testgame.ui.ClickListener;

public class Dummy extends Monster
{
	EnemyAttackResult attacks = new EnemyAttackResult();
	private Animation battleSprite, targeter;
	private ClickListener clicked;
	private int target;
	
	public Dummy(Handler handler, float x, float y, int width, int height, int target, ClickListener clicked)
	{
		super(handler, x, y, width, height);
		
		this.clicked = clicked;
		setTarget(target);
		targeter = new Animation(400, Assests.swordBubble);
		
		if(target != 0)
			setName(getName() + " " + target);
		
		setHealth(10);
		setCurrHP(getHealth());
		setAttack(getWeaponA() + getStrength() + 1);
		setXpDrop(5);
	}
	
	public void battle()
	{
		Random randomGenerator = new Random();
		int ai = randomGenerator.nextInt(2);
		
		if(ai == 0)
		{
			setDialog(getName() + " attacks!");
			setDamage(attacks.normalAttack(getAttack()));
			setDoNothing(false);
			setAnimation(Assests.enemyAttack);
		}
		else
		{
			setDialog(getName() + " smiles...");
			setAnimation(Assests.phAnimation);
			setDialog2("empty");
			setDoNothing(true);
			setDamage(0);
		}
	}

	@Override
	public void tick()
	{
		targeter.tick();
	}

	@Override
	public void render(Graphics g)
	{
		if(hovering && handler.isSelecting())
		{
			Color myColor = new Color(0, 0, 0, 125);
			g.setColor(Color.black);
			g.drawRect(250, 0, 459, 39);
			g.setColor(Color.white);
			g.drawRect(251, 1, 457, 37);
			g.setColor(myColor);
			g.fillRect(252, 2, 456, 36);
			
			g.setColor(Color.white);
			Font font = new Font("Consolas", Font.ROMAN_BASELINE, 16);
			g.setFont(font);
			
			if(getTarget() == 0)
				g.drawString(getName() + " HP: " + getCurrHP(), 260, 20);
			else
				g.drawString(getName() + " " + " HP: " + getCurrHP(), 260, 20);
			
			g.drawImage(targeter.getCurrentFrame(), (int)x + 15, (int)y - 25, 64, 64, null);
		}
		g.drawImage(Assests.slime, (int)x, (int)y, width, height, null);
	}

	@Override
	public void onClick()
	{
		clicked.OnClick();
		
	}
	
}
