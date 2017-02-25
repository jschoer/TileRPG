package testgame.tiles;

import testgame.gfx.Assests;

public class BrickTile extends Tile
{
	public BrickTile(int id)
	{
		super(Assests.brick, id);
	}
	
	@Override
	public boolean isSolid()
	{
		return true;
	}
}
