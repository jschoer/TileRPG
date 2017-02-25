package testgame.entities.statics;

import java.awt.Color;
import java.awt.Graphics;

import testgame.Handler;
import testgame.entities.ClickListener;
import testgame.gfx.Assests;
import testgame.tiles.Tile;

public class Tree extends StaticEntity
{
	
	public Tree(Handler handler, float x, float y)
	{
		super(handler, x, y, Tile.tilewidth, Tile.tileheight);
		bounds.x = 20;
		bounds.y = 40;
		bounds.width = 44;
		bounds.height = 26;
	}

	@Override
	public void tick()
	{
		
	}

	@Override
	public void render(Graphics g)
	{
		g.drawImage(Assests.tree2, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
	//	g.setColor(Color.red);
	//	g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
	}

	@Override
	public void onClick()
	{
		
	}

}
