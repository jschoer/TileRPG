package testgame.worlds;

import java.awt.Color;
import java.awt.Graphics;

import testgame.Handler;
import testgame.battle.BattleManager;
import testgame.battle.monsters.Dummy;
import testgame.battle.monsters.Hero;
import testgame.battle.monsters.Monster;
import testgame.gfx.Assests;

public class Arena
{
	private Handler handler;
	private int width, height;
	private int index = 1;
	private BattleManager battleManager;
	private Boolean battleDone = false;
	
	private Monster player;
	private Monster enemy;
	
	public Arena(Handler handler)
	{
		this.handler = handler;
		battleManager = new BattleManager(handler);
		
		battleManager.addMonster(new Hero(handler, 0, 0, 0, 0));
		player = battleManager.getMonsters().get(0);
	}
	
	public void tick()
	{
		enemy = battleManager.getMonsters().get(index);
		battleManager.tick();
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Assests.battle1, 0, 0, 500, 500, null);
		
		if(!battleDone)
			battleManager.getMonsters().get(index).render(g);
		
		g.drawImage(Assests.health, 0, 0, 100, 50, null);
		
		g.drawString(player.getName() + " Lv. " + player.getLevel(), 7, 17);
		g.drawString("HP: " + player.getCurrHP() + " / " + player.getHealth(), 7, 30);
		g.drawString("MP: " + player.getCurrMP() + " / " + player.getMagic(), 7, 43);
		
	}

	public Handler getHandler()
	{
		return handler;
	}

	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public BattleManager getBattleManager()
	{
		return battleManager;
	}

	public void setBattleManager(BattleManager battleManager)
	{
		this.battleManager = battleManager;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public Boolean getBattleDone()
	{
		return battleDone;
	}

	public void setBattleDone(Boolean battleDone)
	{
		this.battleDone = battleDone;
	}

	public Monster getPlayer()
	{
		return player;
	}

	public void setPlayer(Monster player)
	{
		this.player = player;
	}

	public Monster getEnemy()
	{
		return enemy;
	}

	public void setEnemy(Monster enemy)
	{
		this.enemy = enemy;
	}
	
	
}
