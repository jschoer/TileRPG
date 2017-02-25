package testgame.entities;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;

import testgame.Handler;
import testgame.entities.creatures.Player;
import testgame.ui.UIObject;

public class EntityManager
{
	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private Comparator<Entity> rendorSort = new Comparator<Entity>()
	{
		@Override
		public int compare(Entity a, Entity b)
		{
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1;
			return 1;
		}
		
	};
	
	public EntityManager(Handler handler, Player player)
	{
		this.handler = handler;
		this.player = player;
		
		entities = new ArrayList<Entity>();
		//entities.add(player);
	}
	
	public void tick()
	{
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.tick();
		}
		entities.sort(rendorSort);
	}
	
	public void render(Graphics g)
	{
		for(Entity e : entities)
		{
			e.render(g);
		}
	}
	
	public void onMouseMove(MouseEvent o)
	{
		for(Entity e : entities)
			e.onMouseMove(o);
	}
	
	public void onMouseRelease(MouseEvent o)
	{
		for(Entity e : entities)
			e.onMouseRelease(o);
	}
	
	public void addEntity(Entity e)
	{
		entities.add(e);
	}

	public Handler getHandler()
	{
		return handler;
	}

	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities)
	{
		this.entities = entities;
	}
}
