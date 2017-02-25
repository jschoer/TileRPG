package testgame.worlds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

import testgame.Handler;
import testgame.battle.BattleManager;
import testgame.battle.monsters.KingSlime;
import testgame.battle.monsters.Monster;
import testgame.gfx.Animation;

public class NewArena
{
	private Handler handler;
	private BattleManager battleManager;
	private Monster player;
	
	private int expDropped = 0;
	
	private long lastTime, cooldown = 100, timer = cooldown;
	private int xPos = 0, xPos2 = 0, xPos3 = 0, xPos4 = 0;
	
	private String dialog = "empty";
	private Animation animation;
	private boolean animate = false;
	
	public NewArena(Handler handler, String path, Monster player)
	{
		this.handler = handler;
		this.player = player;
	}
	
	public void tick()
	{
		handler.getMouseManager().setBattleManager(battleManager);
		player.tick();
		
		for(Monster m : battleManager.getMonsters())
			if(m.getCurrHP() > 0)
				m.tick();
		if(animate)
			animation.tick();
	}
	
	public void render(Graphics g)
	{
		//==========================Scenery=============================//
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer >= cooldown)
		{
			timer = 0;
			
			xPos += 10;
			xPos4 += 10;
			
			if(xPos == 500)
				xPos = -400;
			if(xPos4 == 500)
				xPos4 = -100;
		}
		if(timer <= cooldown)
		{
			xPos2++;
			xPos3++;
			
			if(xPos2 == 500)
				xPos2 = -400;
			if(xPos3 == 500)
				xPos3 = 0;
		}
		
		//Sky
		g.setColor(Color.cyan);
		g.fillRect(0, 0, 500, 500);
		
		//Grass
		g.setColor(Color.green);
		g.fillRect(xPos, 400, 400, 100);
		Color myGreen = new Color(0, 200, 0, 125);
		g.setColor(myGreen);
		g.fillRect(xPos2, 400, 400, 100);
		Color finalGreen = new Color(0, 210, 0, 200);
		g.setColor(finalGreen);
		g.fillRect(0, 250, 500, 250);
		
		//Clouds
		g.setColor(Color.white);
		g.fillOval(xPos3, 20, 100, 50);
		g.setColor(Color.white);
		g.fillOval(xPos4, 20, 100, 50);
		//=================================================================//
		
		//=============================Health Bar(s)==============================//
		Color myColor = new Color(0, 0, 0, 125);
		boolean isBright = false;
		if(player.getCurrHP() <= player.getHealth() / 2)
		{
			isBright = true;
			myColor = new Color(255, 255, 0, 150);
		}
		if(player.getCurrHP() <= player.getHealth() / 4)
		{
			isBright = true;
			myColor = new Color(255, 0, 0, 217);
		}
		
		g.setColor(Color.black);
		g.drawRect(0, 0, 159, 79);
		g.setColor(Color.white);
		g.drawRect(1, 1, 157, 77);
		g.setColor(myColor);
		g.fillRect(2, 2, 156, 76);
		
		if(isBright)
			g.setColor(Color.black);
		else
			g.setColor(Color.white);
		Font font = new Font("Consolas", Font.ROMAN_BASELINE, 16);
		g.setFont(font);
		g.drawString(player.getName() + " Lv. " + player.getLevel(), 10, 20);
		g.drawString("HP: " + player.getCurrHP() + "/" + player.getHealth(), 10, 40);
		g.drawString("MP: " + player.getCurrMP() + "/" + player.getMagic(), 10, 60);
		//================================================================================//
		
		g.setColor(myColor);
		
		//Monsters
		for(Monster m : battleManager.getMonsters())
			if(m.getCurrHP() > 0)
				m.render(g);
		
		//==============================Dialog======================//
		if(getDialog() != "empty")
		{
			myColor = new Color(0, 0, 0, 127);
            
			g.drawRect(-1, 400, 501, 100);
			g.setColor(Color.white);
			g.drawRect(-1, 401, 501, 98);
			g.setColor(myColor);
			g.fillRect(0, 402, 500, 97);
			g.setColor(Color.white);
			
			int x = (200 - (dialog.length() / 2 * 5));
			g.drawString(dialog, x, 450);
		}
		
		//============Animations======================//
		if(animate)
		{
			g.drawImage(animation.getCurrentFrame(), 0, 0, 500, 500, null);
		
			if(animation.getIndex() == 5)
			{
				animation.stop();
				animate = false;
			}
		}
		//============================================//
	}
	
	public void update()
	{
		for(Iterator<Monster> iterator = battleManager.getMonsters().iterator(); iterator.hasNext(); ) 
		{
		    Monster m = iterator.next();
		    if(m.getCurrHP() <= 0)
		    {
		    	expDropped += m.getXpDrop();
		        iterator.remove();
		    }
		}
	}

	public Handler getHandler()
	{
		return handler;
	}

	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}

	public BattleManager getBattleManager()
	{
		return battleManager;
	}

	public void setBattleManager(BattleManager battleManager)
	{
		this.battleManager = battleManager;
	}

	public int getExpDropped()
	{
		return expDropped;
	}

	public void setExpDropped(int expDropped)
	{
		this.expDropped = expDropped;
	}

	public Monster getPlayer()
	{
		return player;
	}

	public void setPlayer(Monster player)
	{
		this.player = player;
	}

	public String getDialog()
	{
		return dialog;
	}

	public void setDialog(String dialog)
	{
		this.dialog = dialog;
	}

	public void setAnimation(Animation animation) 
	{
		this.animation = animation;
		
	}
	
	public void setAnimate(boolean animate)
	{
		this.animate = animate;
	}
	
}
