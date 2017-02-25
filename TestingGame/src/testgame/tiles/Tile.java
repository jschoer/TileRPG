package testgame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile
{
	public static Tile[] tiles = new Tile[256];
	public static Tile bigGrassTile = new BigGrassTile(0);
	public static Tile grassTile = new GrassTile(1);
	public static Tile smallGrassTile = new SmallGrassTile(2);
	public static Tile flowerTile = new FlowerTile(3);
	public static Tile brickWallTile = new BrickWallTile(4);
	public static Tile brickTile = new BrickTile(5);
	public static Tile carpetTile = new CarpetTile(6);
	
	public static final int tilewidth = 64, tileheight = 64;
	
	protected BufferedImage texture;
	protected final int id;
	
	public Tile(BufferedImage texture, int id)
	{
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g, int x, int y)
	{
		g.drawImage(texture, x, y, tilewidth, tileheight, null);
	}
	
	public boolean isSolid()
	{
		return false;
	}
	
	public int getId()
	{
		return id;
	}
}
