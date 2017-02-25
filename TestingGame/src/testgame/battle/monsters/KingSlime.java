package testgame.battle.monsters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import testgame.Handler;
import testgame.gfx.Animation;
import testgame.gfx.Assests;
import testgame.ui.ClickListener;

public class KingSlime extends Monster
{
	private Animation battleSprite, targeter;
	EnemyAttackResult attacks = new EnemyAttackResult();
	private ClickListener clicked;

	public KingSlime(Handler handler, float x, float y, int width, int height, int target, ClickListener clicked)
	{
		super(handler, x, y, width, height);
		
		this.clicked = clicked;
		setTarget(target);
		
		battleSprite = new Animation(200, Assests.slimeKing);
		targeter = new Animation(400, Assests.swordBubble);
		setName("King Slime");
		
		setHealth(100);
		setCurrHP(100);
		
		setAttack(7);
		setIntelligence(5);
		setDefense(5);
		setSpeed(8);
		
		setGdrop(20);
		setXpDrop(50);
	}

	@Override
	public void battle()
	{
		Random randomGenerator = new Random();
		int ai = randomGenerator.nextInt(3);
		
		if(ai == 0)
		{
			setDialog(getName() + " attacks!");
			setDamage(attacks.normalAttack(getAttack()));
			setAnimation(Assests.enemyAttack);
			setDoNothing(false);
		}
		else if(ai == 1)
		{
			setDialog(getName() + " tackles " + handler.getArena().getPlayer().getName() + "!");
			setAnimation(Assests.enemyAttack);
			setDamage(attacks.tackleAttack(getAttack()));
			setDoNothing(false);
		}
		else if(ai == 2)
		{
			setDialog(getName() + " casts Fire!");
			setAnimation(Assests.enemyAttack);
			setDamage(attacks.fireBall(getIntelligence()));
			setDoNothing(false);
		}
		
	}

	@Override
	public void tick()
	{
		battleSprite.tick();
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
			
			g.drawString(getName() + " HP: " + getCurrHP(), 260, 20);
			g.drawImage(targeter.getCurrentFrame(), (int)x + 75, (int)y - 50, 64, 64, null);
		}
		g.drawImage(battleSprite.getCurrentFrame(), (int)x, (int)y, width, height, null);
		
	}

	@Override
	public void onClick()
	{
		clicked.OnClick();
	}

}
