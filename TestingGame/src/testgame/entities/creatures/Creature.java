package testgame.entities.creatures;

import testgame.Game;
import testgame.Handler;
import testgame.entities.Entity;
import testgame.tiles.Tile;

public abstract class Creature extends Entity
{
	public static final float default_speed = 3.0f;
	public static final int default_creature_width = 64, default_creature_height = 64;
	
	protected float speed;

	protected float xMove, yMove;

	public Creature(Handler handler, float x, float y, int width, int height)
	{
		super(handler, x, y, width, height);
		
		speed = default_speed;
		
		xMove = 0;
		yMove = 0;
	}
	
	public void moveX()
	{
		if(xMove > 0)
		{
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.tilewidth;
			if(!collisionWithTile(tx,(int)(y + bounds.y) / Tile.tileheight) &&
					!collisionWithTile(tx, (int)(y + bounds.y + bounds.height) / Tile.tileheight))
			{
				x += xMove;
			}
			else
			{
				x = tx * Tile.tilewidth - bounds.x - bounds.width - 1;
			}
		}
		else if (xMove < 0)
		{
			int tx = (int) (x + xMove + bounds.x) / Tile.tilewidth;
			if(!collisionWithTile(tx,(int)(y + bounds.y) / Tile.tileheight) &&
					!collisionWithTile(tx, (int)(y + bounds.y + bounds.height) / Tile.tileheight))
			{
				x += xMove;
			}
			else
			{
				x = tx * Tile.tilewidth + Tile.tilewidth - bounds.x;
			}
		}
	}
	
	public void moveY()
	{
		if(yMove < 0)
		{
			int ty = (int)(y + yMove + bounds.y) / Tile.tileheight;
			
			if(!collisionWithTile((int)(x + bounds.x) / Tile.tilewidth, ty) &&
					!collisionWithTile((int)(x + bounds.x + bounds.width) / Tile.tilewidth, ty))
			{
				y += yMove;
			}
			else 
			{
				y = ty * Tile.tileheight + Tile.tileheight - bounds.y;
			}
		}
		else if (yMove > 0)
		{
			int ty = (int)(y + yMove + bounds.y + bounds.height) / Tile.tileheight;
			
			if(!collisionWithTile((int)(x + bounds.x) / Tile.tilewidth, ty) &&
					!collisionWithTile((int)(x + bounds.x + bounds.width) / Tile.tilewidth, ty))
			{
				y += yMove;
			}
			else
			{
				y = ty * Tile.tileheight - bounds.y - bounds.height - 1;
			}
		}
	}

	public void move()
	{
		if(!checkEntityCollision(xMove, 0f))
			moveX();
		if(!checkEntityCollision(0f, yMove))
			moveY();
	}
	
	protected boolean collisionWithTile(int x, int y)
	{
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	public float getxMove()
	{
		return xMove;
	}

	public void setxMove(float xMove)
	{
		this.xMove = xMove;
	}

	public float getyMove()
	{
		return yMove;
	}

	public void setyMove(float yMove)
	{
		this.yMove = yMove;
	}
	public float getSpeed()
	{
		return speed;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
}
