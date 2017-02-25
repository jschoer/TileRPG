package testgame.worlds;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import testgame.Game;
import testgame.Handler;
import testgame.battle.monsters.Dummy;
import testgame.entities.ClickListener;
import testgame.entities.EntityManager;
import testgame.entities.creatures.MaleVillager;
import testgame.entities.creatures.Player;
import testgame.entities.creatures.Slime;
import testgame.entities.statics.Door;
import testgame.entities.statics.Pot;
import testgame.entities.statics.Tree;
import testgame.sound.BGMplayer;
import testgame.states.State;
import testgame.tiles.Tile;
import testgame.utils.Utils;

public class World
{
	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private int[][] tiles;
	
	//Entities
	private EntityManager entityManager;
	
	private String[] dialog = {"Placeholder text"};
	
	public World(Handler handler, String path)
	{
		this.handler = handler;
		
		loadWorld(path);
		
		entityManager = new EntityManager(handler, handler.getPlayer());
		entityManager.addEntity(new Player(handler, spawnX, spawnY, new ClickListener() {
			@Override
			public void OnClick()
			{
				//System.out.println("Let's Go!");
			}}));
		
		if(handler.getLocalIndex() == 0)
			loadTownFirst();
		else if(handler.getLocalIndex() == 1)
			loadOverworld1();
	}
	
	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	public void tick()
	{
		handler.getMouseManager().setEntityManager(entityManager);
		entityManager.tick();
		
	}
	
	public void render(Graphics g)
	{
		int xStart = (int)(Math.max(0, handler.getGameCamera().getxOffset() / Tile.tilewidth));
		int xEnd = (int)(Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.tilewidth + 1));
		int yStart = (int)(Math.max(0, handler.getGameCamera().getyOffset() / Tile.tileheight));;
		int yEnd = (int)(Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.tileheight + 1));
		
		for(int y = yStart; y < yEnd; y++)
		{
			for(int x = xStart; x < xEnd; x++)
			{
				getTile(x, y).render(g, (int)(x * Tile.tilewidth - handler.getGameCamera().getxOffset()),
										(int)(y * Tile.tileheight - handler.getGameCamera().getyOffset()));
			}
		}
		entityManager.render(g);
		
		//Day and night time cycle!
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);  
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		if(hours >= 18)
		{
			Color myColor = new Color(0, 0, 0, 100);
			g.setColor(myColor);
			g.fillRect(0, 0, 500, 500);
		}
			
	}
	
	public Tile getTile(int x, int y)
	{
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.grassTile;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null)
		{
			return Tile.grassTile;
		}
		return t;
	}
	
	
	private void loadWorld(String path)
	{
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
		
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void loadTownFirst()
	{
		entityManager.addEntity(new Tree(handler, 64, 96));
		
		entityManager.addEntity(new MaleVillager(handler, 200, 200, width, height, 0, new ClickListener() {
			@Override
			public void OnClick()
			{
				handler.setTalking(true);
				//handler.setDialog("I won't stop running!");
				dialog = new String[1];
				dialog[0] = "I won't stop running!";
				handler.setDialog(dialog);
				handler.setDialogLength(dialog.length);
				
			}}));
		
		entityManager.addEntity(new MaleVillager(handler, 1000,500,width,height, 1, new ClickListener() {
			@Override
			public void OnClick()
			{
				handler.setTalking(true);
				//handler.setDialog("IMA FREEKIN OOUT MAAN!?11?");
				dialog = new String[1];
				dialog[0] = "I'm freaking out, man!";
				handler.setDialog(dialog);
				handler.setDialogLength(dialog.length);
				
			}}));
		
		entityManager.addEntity(new MaleVillager(handler, 900, 200,width,height, 3, new ClickListener() {
			@Override
			public void OnClick()
			{
				handler.setTalking(true);
				//handler.setDialog("Sorry, but the shop is closed.");
				dialog = new String[2];
				dialog[0] = "Sorry, but the shop is closed.";
				dialog[1] = "We will open when Josh programs us to.";
				handler.setDialog(dialog);
				handler.setDialogLength(dialog.length);
				
			}}));
		
		entityManager.addEntity(new MaleVillager(handler, 200, 100,width,height, 3, new ClickListener() {
			@Override
			public void OnClick()
			{
				handler.setTalking(true);
				//handler.setDialog("This is my favorite part of the village.");
				dialog = new String[1];
				dialog[0] = "This is my favorite part of the village.";
				handler.setDialog(dialog);
				handler.setDialogLength(dialog.length);
				
			}}));
		
		entityManager.addEntity(new MaleVillager(handler, 1800, 950,width,height, 3, new ClickListener() {
			@Override
			public void OnClick()
			{
				handler.setTalking(true);
				//handler.setDialog("Welcome to my home town!");
				dialog = new String[2];
				dialog[0] = "Welcome to our humble village.";
				dialog[1] = "Please make yourself at home.";
				handler.setDialog(dialog);
				handler.setDialogLength(dialog.length);
				
			}}));
		
		entityManager.addEntity(new MaleVillager(handler, 375, 640, width, height, 3, new ClickListener() {
			@Override
			public void OnClick()
			{
				handler.setTalking(true);
				dialog = new String[2];
				
				dialog[0] = "You look hurt, let me heal you.";
				dialog[1] = handler.getArena().getPlayer().getName() + " is fully healed!";
				
				handler.getNewArena().getPlayer().setCurrHP(handler.getNewArena().getPlayer().getHealth());
				handler.getNewArena().getPlayer().setCurrMP(handler.getNewArena().getPlayer().getMagic());
				
				handler.setDialog(dialog);
				handler.setDialogLength(dialog.length);
				
			}}));
		
		entityManager.addEntity(new MaleVillager(handler, 1946, 280, width, height, 3, new ClickListener() {
			@Override
			public void OnClick()
			{
				handler.setTalking(true);
				dialog = new String[3];
				
				dialog[0] = "I'm the mayor of this village.";
				dialog[1] = "Hax mode on!";
				dialog[2] = "Now go and kill a monster to see.";
				
				handler.getNewArena().getPlayer().setExp(1000000);
				
				handler.setDialog(dialog);
				handler.setDialogLength(dialog.length);
				
			}}));
		
		entityManager.addEntity(new Door(handler, 1664, 640, 64, 64));
		entityManager.addEntity(new Door(handler, 384, 832, 64, 64));
		entityManager.addEntity(new Door(handler, 768, 384, 64, 64));
		
		entityManager.addEntity(new Pot(handler, 900, 320));
	}
	public void loadOverworld1()
	{
		entityManager.addEntity(new Tree(handler, 100, 200));
		entityManager.addEntity(new MaleVillager(handler, 200, 64, width, height, 3, new ClickListener() {
			@Override
			public void OnClick()
			{
				handler.setTalking(true);
				dialog = new String[2];
				dialog[0] = "This is the overworld!";
				dialog[1] = "Be careful of wild monsters.";
				handler.setDialog(dialog);
				handler.setDialogLength(dialog.length);
				
			}}));
		entityManager.addEntity(new Slime(handler, 300, 100, width, height, 3, new ClickListener() {
			@Override
			public void OnClick()
			{
				dialog = new String[1];
				dialog[0] = "I am the king of slimes!";
				
				handler.setTalking(true);
				handler.setDialog(dialog);
				handler.setDialogLength(dialog.length);
				
				handler.setBoss(true);
			}}));
	}
	
}
