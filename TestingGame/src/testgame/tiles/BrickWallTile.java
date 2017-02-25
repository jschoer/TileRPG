package testgame.tiles;

import testgame.gfx.Assests;

public class BrickWallTile extends Tile
{
	public BrickWallTile(int id)
	{
		super(Assests.brickWall, id);
	}
	
	@Override
	public boolean isSolid()
	{
		return true;
	}
}
