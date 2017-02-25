package testgame.entities.statics;

import java.awt.Graphics;

import testgame.Handler;
import testgame.gfx.Assests;
import testgame.tiles.Tile;

public class Pot extends StaticEntity
{

	public Pot(Handler handler, float x, float y)
	{
		super(handler, x, y, Tile.tilewidth, Tile.tileheight);
	}

	@Override
	public void tick()
	{
		
	}

	@Override
	public void render(Graphics g)
	{
		g.drawImage(Assests.pot, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	@Override
	public void onClick()
	{
		// TODO Auto-generated method stub
		
	}

}
