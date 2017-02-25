package testgame.battle;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import testgame.Handler;

public abstract class Battle
{
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	
	protected Rectangle bounds;
	protected boolean hovering; 
	
	public Battle(Handler handler, float x, float y, int width, int height)
	{

		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void onClick();
	
	public void onMouseMove(MouseEvent e)
	{
		if(bounds.contains(e.getX() - x, e.getY() + - y))
			hovering = true;
		else
			hovering = false;
	}
	
	public void onMouseRelease(MouseEvent e)
	{
		if(hovering)
			onClick();
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

	public Handler getHandler()
	{
		return handler;
	}

	public void setHandler(Handler handler)
	{
		this.handler = handler;
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
	
	
}
