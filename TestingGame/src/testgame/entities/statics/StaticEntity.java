package testgame.entities.statics;

import java.awt.Rectangle;

import testgame.Handler;
import testgame.entities.Entity;

public abstract class StaticEntity extends Entity
{
	private boolean open = false;
	
	public StaticEntity(Handler handler, float x, float y, int width, int height)
	{
		super(handler, x, y, width, height);
	}
	
	public void checks()
	{
		Rectangle rect1 = new Rectangle((int)(handler.getPlayer().getX() + handler.getPlayer().getBounds().x + handler.getPlayer().getxMove()),
				(int)(handler.getPlayer().getY() + handler.getPlayer().getBounds().y + handler.getPlayer().getyMove()),
				handler.getPlayer().getBounds().width, handler.getPlayer().getBounds().height);
		Rectangle rect2 = new Rectangle((int)(x + clickBounds.x), (int)(y + clickBounds.y), clickBounds.width, clickBounds.height);
		
		if(rect1.intersects(rect2))
			setOpen(true);
		else
			setOpen(false);
	}

	public boolean isOpen()
	{
		return open;
	}

	public void setOpen(boolean open)
	{
		this.open = open;
	}
	
}
