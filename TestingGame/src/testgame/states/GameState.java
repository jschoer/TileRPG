package testgame.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import testgame.Game;
import testgame.Handler;
import testgame.battle.BattleManager;
import testgame.battle.monsters.Dummy;
import testgame.battle.monsters.Hero;
import testgame.battle.monsters.KingSlime;
import testgame.battle.monsters.Monster;
import testgame.entities.creatures.Player;
import testgame.gfx.Assests;
import testgame.sound.BGMplayer;
import testgame.tiles.Tile;
import testgame.ui.ClickListener;
import testgame.ui.UIImageButton;
import testgame.ui.UIManager;
import testgame.worlds.NewArena;
import testgame.worlds.World;

public class GameState extends State
{
	//World setup
	private World world;
	private UIManager uiManager, menuManager;
	private BGMplayer bgmPlayer;
	
	//RNG to run into an enemy
	private Random randomGenerator = new Random();
	private int encounterRate = randomGenerator.nextInt(1000) + 500;
	
	//Keep check of players level, temporary
	private boolean[] levels;
	private int index = 0;
	
	//Open and close the menu
	private boolean menu = false;
	
	//Dialog timer
	private Timer timer;
	
	//Positions
	private float xPos, yPos;
	
	//Location Tracking
	private String[] location = {"resource/worlds/town1.txt","resource/worlds/overworld1.txt"};
	private int localIndex = 0;
	
	//Dialog Stuff
	private String[] dialog = null;
	private int dialogLength;
	private int dialogIndex;
	private boolean done = true;
	private int xStr = 10;
	private long lastClick, clickCooldown = 800, clickTimer = 0;
	
	//Battle Transition Animation
	private boolean battleStart = false;
	private boolean newbattleStart = false;
	private int xRect, yRect, xGrow = 0, yGrow = 0;
	
	public GameState(Handler handler)
	{
		super(handler);
		
		handler.setLocalIndex(localIndex);
		world = new World(handler, location[localIndex]);
		handler.setWorld(world);
		
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		menuManager = new UIManager(handler);
		
		bgmPlayer = handler.getBgmPlayer();
		
		addUI();
		addMenu();
		
		levels = new boolean[100];
		
		handler.setBattleDone(true);
		handler.setTalking(false);
		
		NewArena newArena = null;
		if(handler.getNewArena() == null)
		{
			newArena = new NewArena(handler, "/textures/battle.png", new Hero(handler, 0, 0, 0, 0));
		}
		handler.setNewArena(newArena);
		
		//set up the timer to run for 4 seconds
		timer = new Timer(4000, null);
		timer.setRepeats(false);
	}

	@Override
	public void tick()
	{
		handler.getNewArena().getPlayer().tick();
		if(menu)
		{
			handler.getMouseManager().setUIManager(menuManager);
			menuManager.tick();
		}
		else if(battleStart || newbattleStart)
		{
			//do nothing
		}
		else
		{
			handler.getMouseManager().setUIManager(uiManager);
			uiManager.tick();
			world.tick();
		}
		
		//player position
		xPos = handler.getPlayer().getX();
		yPos = handler.getPlayer().getY();
		
		checkLocation();
		
		//Random Encounter Code
		randomEncounters();
		
		//dialog handling
		if(handler.isTalking() && done)
		{
			dialog = handler.getDialog();
			dialogLength = handler.getDialogLength();
			dialogIndex = 0;
			done = false;
		}
		
		//Level Up System
		if((handler.getArena().getPlayer().getExp() >= (10 * (index + 1))) && (index < 99) && done)
		{
			levelUp();
		}
		
		clickTimer += System.currentTimeMillis() - lastClick;
		lastClick = System.currentTimeMillis();
		if(handler.getMouseManager().isRightPressed() && !(clickTimer < clickCooldown))
		{
			clickTimer = 0;
			startNewBattle();
		}
	}
	
	@Override
	public void render(Graphics g)
	{
		world.render(g);
		
		if(handler.getMouseManager().getMouseY() <= 50 && !menu)
			uiManager.render(g);
		else if(menu)
		{
			//Draw box for stat menu
			Color myColor = new Color(0, 0, 0, 157);
			g.setColor(Color.black);
			g.drawRect(300, 0, 199, 499);
			g.setColor(Color.white);
			g.drawRect(301, 1, 197, 497);
			g.setColor(myColor);
			g.fillRect(302, 2, 196, 496);
			
			//Display stats
			g.setColor(Color.white);
			Font font = new Font("Consolas", Font.ROMAN_BASELINE, 16);
			g.setFont(font);
		/*	String txt = handler.getArena().getPlayer().getName() + "\nLv. " + handler.getArena().getPlayer().getLevel()
					+ "\n\nHP: " + handler.getArena().getPlayer().getCurrHP() + "/" + handler.getArena().getPlayer().getHealth()
					+ "\n\nMP: " + handler.getArena().getPlayer().getCurrMP() + "/" + handler.getArena().getPlayer().getMagic()
					+ "\n\nAttack\n" + handler.getArena().getPlayer().getAttack() + "\n\nDefense\n" + handler.getArena().getPlayer().getDefense()
					+ "\n\nIntelligence\n" + handler.getArena().getPlayer().getIntelligence() + "\n\nSpeed\n" + handler.getArena().getPlayer().getSpeed()
					+ "\n\nGold\n" + handler.getArena().getPlayer().getGold()
					+ "\n\nTotal Exp\n" + handler.getArena().getPlayer().getExp();
			int x = 310;
			int y = 0;
			for (String line : txt.split("\n"))
	            g.drawString(line, x, y += g.getFontMetrics().getHeight());*/
			
			String txt2 = handler.getNewArena().getPlayer().getName() + "\nLv. " + handler.getNewArena().getPlayer().getLevel()
					+ "\n\nHP: " + handler.getNewArena().getPlayer().getCurrHP() + "/" + handler.getNewArena().getPlayer().getHealth()
					+ "\n\nMP: " + handler.getNewArena().getPlayer().getCurrMP() + "/" + handler.getNewArena().getPlayer().getMagic()
					+ "\n\nAttack\n" + handler.getNewArena().getPlayer().getAttack() + "\n\nDefense\n" + handler.getNewArena().getPlayer().getDefense()
					+ "\n\nIntelligence\n" + handler.getNewArena().getPlayer().getIntelligence() + "\n\nSpeed\n" + handler.getNewArena().getPlayer().getSpeed()
					+ "\n\nGold\n" + handler.getNewArena().getPlayer().getGold()
					+ "\n\nTotal Exp\n" + handler.getNewArena().getPlayer().getExp();
			int x2 = 310;
			int y2 = 0;
			for (String line : txt2.split("\n"))
	            g.drawString(line, x2, y2 += g.getFontMetrics().getHeight());
			menuManager.render(g);
		}
		else
		{
			Color myColor = new Color(0, 0, 0, 157);
			g.setColor(myColor);
			g.fillRect(0, 0, 500, 20);
			g.setColor(Color.white);
			Font font = new Font("Consolas", Font.ROMAN_BASELINE, 16);
			g.setFont(font);
			
			//Display Position
			g.drawString("X: " + (int)xPos + " Y: " + (int)yPos, 10, 15);
			
			//Display Menu
			g.drawString("Menu", 230, 15);
			//g.drawString("" + encounterRate, 450, 15);
			
			//Display Current Time
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date); 
			if(cal.get(Calendar.MINUTE) > 9)
				g.drawString(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE), 450, 15);
			else
				g.drawString(cal.get(Calendar.HOUR_OF_DAY) + ":0" + cal.get(Calendar.MINUTE), 450, 15);
		}
		
		//========================DIALOG===============================================//
		if(handler.isTalking())// && dialogIndex < dialogLength && dialog != null)
		{
			clickTimer += System.currentTimeMillis() - lastClick;
			lastClick = System.currentTimeMillis();
			
			Color myColor = new Color(0, 0, 0, 127);
            
			g.drawRect(-1, 400, 501, 99);
			g.setColor(Color.white);
			g.drawRect(-1, 401, 501, 97);
			g.setColor(myColor);
			g.fillRect(0, 402, 500, 96);
			
			Font font = new Font("Consolas", Font.ROMAN_BASELINE, 17);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString(dialog[dialogIndex], xStr, 450);
		}
		if(handler.getMouseManager().isLeftPressed() && !(clickTimer < clickCooldown) && handler.isTalking())
		{
			bgmPlayer.playSound("/sound/sfx/gainlp.wav");
			clickTimer = 0;
			dialogIndex++;
			if(dialogIndex == dialogLength)
				done = true;
		}
		if(done && !battleStart && !newbattleStart)
		{
			handler.setTalking(false);
			xStr = 10;
			dialogIndex = 0;
			dialogLength = 0;
			dialog = null;
			
			if(handler.isBoss())
				startNewBattle();
		}
		
		//========================Battle Transition====================//
		if(battleStart)
		{	
			g.setColor(Color.black);
			g.fillRect(xRect, yRect, xGrow, yGrow);
			xRect -= 10;
			yRect -= 10;
			xGrow += 20;
			yGrow += 20;
			
			if(!timer.isRunning())
			{
				xRect = 250;
				yRect = 250;
				xGrow = 0;
				yGrow = 0;
				battleStart = false;
				timer.setInitialDelay(4000);
				State.setState(handler.getGame().battleState);
			}
		}
		if(newbattleStart)
		{	
			g.setColor(Color.black);
			g.fillRect(xRect, yRect, xGrow, yGrow);
			xRect -= 10;
			yRect -= 10;
			xGrow += 20;
			yGrow += 20;
			
			if(!timer.isRunning())
			{
				xRect = 250;
				yRect = 250;
				xGrow = 0;
				yGrow = 0;
				newbattleStart = false;
				handler.getMouseManager().setEntityManager(null);
				timer.setInitialDelay(4000);
				State.setState(handler.getGame().newBattleState);
			}
		}
	}
	
	public void addUI()
	{
		uiManager.addObject(new UIImageButton(254, 0, 32, 32, Assests.backBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().menuState);
			}}));
		uiManager.addObject(new UIImageButton(296, 0, 32, 32, Assests.exitBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				int close = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit now?");
				if(close == 0)
					System.exit(0);
			}}));
		uiManager.addObject(new UIImageButton(202, 0, 32, 32, Assests.saveBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				try
				{
					ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("game.dat"));
					output.close();
				}
				catch(IOException ex)
				{
					System.out.println("Save failed.");
				}
				handler.setTalking(true);
				dialog = new String[1];
				dialog[0] = "Saved! Not Really...";
				handler.setDialog(dialog);
				handler.setDialogLength(dialog.length);
				xStr = 200;
			}}));
		uiManager.addObject(new UIImageButton(160, 0, 32, 32, Assests.partyBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				menu = true;
			}}));
	}
	
	public void addMenu()
	{
		menuManager.addObject(new UIImageButton(468, 0, 32, 32, Assests.backBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				menu = false;
			}}));
	}
	
	public void levelUp()
	{
		int increment;
		Random random = new Random();
		
		handler.getArena().getPlayer().setLevel(handler.getArena().getPlayer().getLevel() + 1);
		
		increment = random.nextInt(2) + 1;
		handler.getArena().getPlayer().setStrength(handler.getArena().getPlayer().getStrength() + increment);
		
		increment = random.nextInt(1) + 1;
		handler.getArena().getPlayer().setResilience(handler.getArena().getPlayer().getResilience() + increment);
		
		increment = random.nextInt(1) + 1;
		handler.getArena().getPlayer().setIntelligence(handler.getArena().getPlayer().getIntelligence() + increment);
		
		increment = random.nextInt(5);
		handler.getArena().getPlayer().setMagic(handler.getArena().getPlayer().getMagic() + increment);
		
		increment = random.nextInt(3);
		handler.getArena().getPlayer().setSpeed(handler.getArena().getPlayer().getSpeed() + increment);
		
		increment = random.nextInt(10) + 2;
		handler.getArena().getPlayer().setHealth(handler.getArena().getPlayer().getHealth() + increment);
		
		handler.getArena().getPlayer().setCurrHP(handler.getArena().getPlayer().getHealth());
		handler.getArena().getPlayer().setCurrMP(handler.getArena().getPlayer().getMagic());
		
		levels[index] = true;
		index++;
		
		handler.getArena().getPlayer().tick();
		
		handler.setTalking(true);
		dialog = new String[1];
		dialog[0] = "Level Up!";
		handler.setDialog(dialog);
		handler.setDialogLength(dialog.length);
		xStr = 200;
	}
	
	public void checkLocation()
	{
		if(yPos >= 1280 && localIndex == 0)
		{
			localIndex = 1;
			handler.setLocalIndex(localIndex);
			world = new World(handler, location[localIndex]);
			handler.setWorld(world);
			
			timer.stop();
			
			bgmPlayer.stopMusic();
			bgmPlayer.playMusic("/sound/bgm/overworld.wav");
		}
		else if(yPos <= 0 && localIndex == 1)
		{
			localIndex = 0;
			handler.setLocalIndex(localIndex);
			world = new World(handler, location[localIndex]);
			handler.setWorld(world);
			handler.getPlayer().setX(1700);
			handler.getPlayer().setY(1190);
			
			timer.stop();
			
			bgmPlayer.stopMusic();
			bgmPlayer.playMusic("/sound/bgm/town.wav");
		}
	}
	
	public void randomEncounters()
	{
		if(handler.getPlayer().isWalking() && localIndex != 0 && !handler.isTalking() && !battleStart)
		{
			encounterRate -= handler.getPlayer().getSpeed();
		}
		if(encounterRate <= 0)
		{
			startNewBattle();
			
			randomGenerator = new Random();
			encounterRate = randomGenerator.nextInt(1000);
			encounterRate += 500;
		}
		handler.getPlayer().setWalking(false);
		if(handler.getKeyManager().test && !battleStart)
		{
			startNewBattle();
		}
	}
	
	public void startBattle(Monster m)
	{
		handler.getArena().getBattleManager().addMonster(m);
		handler.getArena().setBattleDone(false);
		handler.setBattleDone(true);
		handler.getMouseManager().setUIManager(null);
		handler.getMouseManager().setEntityManager(null);
		
		bgmPlayer.playSound("/sound/sfx/set.wav");
		bgmPlayer.pauseMusic();
		
		if(handler.isBoss())
			bgmPlayer.playMusic("/sound/bgm/boss.wav");
		else
			bgmPlayer.playMusic("/sound/bgm/battle.wav");
		
		xRect = 250;
		yRect = 250;
		battleStart = true;
		timer.setInitialDelay(1000);
		timer.restart();
	}
	
	public void startNewBattle()
	{
		//determine the number of enemies
		Random rand = new Random();
		int numOfEnemies = rand.nextInt(8) + 1;
		
		//create the new battle world
		NewArena newArena = null;
		if(handler.getNewArena() == null)
		{
			newArena = new NewArena(handler, "/textures/battle.png", new Hero(handler, 0, 0, 0, 0));
		}
		else
		{
			newArena = new NewArena(handler, "/textures/battle.png", handler.getNewArena().getPlayer());
			handler.setNewArena(null);
		}
		handler.setNewArena(newArena);
		
		//create the battle manager to hold all the enemies
		BattleManager battleManager = new BattleManager(handler);
		
		int x = 175;
		int y = 175;
		switch(numOfEnemies)
		{
		case 1: x = 175; break;
		case 2: x = 150; break;
		case 3: x = 125; break;
		case 4: x = 50; break;
		case 5: x = 50; break;
		case 6: x = 50; break;
		case 7: x = 50; break;
		case 8: x = 50; break;
		}
		
		if(numOfEnemies >= 5)
			y = 255;
		int reset = x;
		if(!handler.isBoss())
		{
			for(int i = 0; i < numOfEnemies; i++)
			{
				battleManager.addMonster(generateMonster(x, y, i));
				x += 110;
				if(i == 3)
				{
					y = 150;
					x = reset;
				}
			}
		}
		else
			battleManager.addMonster(generateMonster(135, 110, 0));
		
		handler.getNewArena().setBattleManager(battleManager);
		
		bgmPlayer.pauseMusic();
		bgmPlayer.playSound("/sound/sfx/set.wav");
		if(handler.isBoss())
			bgmPlayer.playMusic("/sound/bgm/boss.wav");
		else
			bgmPlayer.playMusic("/sound/bgm/battle.wav");
		
		xRect = 250;
		yRect = 250;
		newbattleStart = true;
		timer.setInitialDelay(1000);
		timer.restart();
	}
	
	public Monster generateMonster(int x, int y, int target)
	{
		if(!handler.isBoss())
		{
			if(handler.getLocalIndex() == 1)
				return (new Dummy(handler, x, y, 100, 100, target, new ClickListener() {
					@Override
					public void OnClick()
					{
						handler.setTarget(target);
					}}));
		}
		else
		{
			if(handler.getLocalIndex() == 1)
				return (new KingSlime(handler, x, y, 200, 200, target, new ClickListener() {
					@Override
					public void OnClick()
					{
						handler.setTarget(target);
					}}));	
		}
			
		return (new Dummy(handler, x, y, 100, 100, target, new ClickListener() {

			@Override
			public void OnClick()
			{
				handler.setTarget(target);
			}}));
	}
}
