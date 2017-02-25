package testgame.battle;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import testgame.Handler;
import testgame.battle.monsters.Monster;
import testgame.ui.UIObject;

public class BattleManager
{
	private Handler handler;
	private ArrayList<Monster> monsters;
	
	public BattleManager(Handler handler)
	{
		this.handler = handler;
		
		monsters = new ArrayList<Monster>();
	}
	
	public void onMouseMove(MouseEvent e)
	{
		for(Monster m : monsters)
			if(m.getCurrHP() > 0)
				m.onMouseMove(e);
	}
	
	public void onMouseRelease(MouseEvent e)
	{
		for(Monster m : monsters)
			if(m.getCurrHP() > 0)
				m.onMouseRelease(e);
	}
	
	public void tick()
	{
		for(int i = 0; i < monsters.size(); i++)
		{
			Monster e = monsters.get(i);
			e.tick();
		}
	}
	
	public void render(Graphics g)
	{
		for(Monster e : monsters)
		{
			e.render(g);
		}
	}
	
	public void addMonster(Monster e)
	{
		monsters.add(e);
	}
	
	public void removeMonster(Monster e)
	{
		monsters.remove(e);
	}

	public Handler getHandler()
	{
		return handler;
	}

	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}

	public ArrayList<Monster> getMonsters()
	{
		return monsters;
	}

	public void setMonsters(ArrayList<Monster> monsters)
	{
		this.monsters = monsters;
	}
	
	
}
