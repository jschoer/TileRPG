package testgame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import testgame.Game;
import testgame.Handler;

public abstract class Entity
{
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected boolean hovering;
	
	protected Rectangle bounds, clickBounds;
	
	public Entity(Handler handler, float x, float y, int width, int height)
	{
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		bounds = new Rectangle(0,0,width,height);
		clickBounds = new Rectangle(0, 0, width, height);
	}

	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void onClick();
	
	public void onMouseMove(MouseEvent e)
	{
		if(clickBounds.contains(e.getX() + (int)handler.getGameCamera().getxOffset() - x, e.getY() + (int)handler.getGameCamera().getyOffset() - y))
			hovering = true;
		else
			hovering = false;
	}
	
	public void onMouseRelease(MouseEvent e)
	{
		if(hovering)
			onClick();
	}
	
	public boolean checkEntityCollision(float xOffset, float yOffset)
	{
		for(Entity e : handler.getWorld().getEntityManager().getEntities())
		{
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset)
	{
		return new Rectangle((int)(x + bounds.x + xOffset), (int)(y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
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

	public Handler getHandler()
	{
		return handler;
	}

	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}

	public boolean isHovering()
	{
		return hovering;
	}

	public void setHovering(boolean hovering)
	{
		this.hovering = hovering;
	}

	public Rectangle getBounds()
	{
		return bounds;
	}

	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
	}

	public Rectangle getClickBounds()
	{
		return clickBounds;
	}

	public void setClickBounds(Rectangle clickBounds)
	{
		this.clickBounds = clickBounds;
	}
	
}
