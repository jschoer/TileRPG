package testgame.entities.statics;

import java.awt.Graphics;

import testgame.Handler;
import testgame.entities.ClickListener;
import testgame.gfx.Assests;

public class Door extends StaticEntity
{	
	public Door(Handler handler, float x, float y, int width, int height)
	{
		super(handler, x, y, width, height);
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 64;
		bounds.height = 64;
		
		clickBounds.x = 0;
		clickBounds.y = 0;
		clickBounds.width = 64;
		clickBounds.height = 64;
	}

	@Override
	public void tick()
	{			
		checks();
		
		if(!isOpen())
		{
			bounds.x = 0;
			bounds.y = 0;
			bounds.width = 64;
			bounds.height = 64;
		}
		else
		{
			bounds.x = 0;
			bounds.y = 0;
			bounds.width = 0;
			bounds.height = 0;
		}
	}

	@Override
	public void render(Graphics g)
	{
		if(!isOpen())
			g.drawImage(Assests.door[0], (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
		else
			g.drawImage(Assests.door[1], (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	@Override
	public void onClick()
	{
		
	}
	
}
