package testgame.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import testgame.Handler;
import testgame.battle.monsters.Dummy;
import testgame.battle.monsters.Monster;
import testgame.gfx.Animation;
import testgame.gfx.Assests;
import testgame.sound.BGMplayer;
import testgame.ui.ClickListener;
import testgame.ui.UIImageButton;
import testgame.ui.UIManager;
import testgame.worlds.Arena;

public class BattleState extends State
{
	private UIManager uiManager;
	private Arena arena;
	private BGMplayer bgmPlayer;
	
	private Monster player, enemy;
	
	private Boolean turnStart = false;	//denotes the start of the turn
	private Boolean playerDone = false;	//tells when the player is done with his/her action
	private Boolean enemyDone = false;	//tells when the enemy is done
	private int turns = 0;				//the number of turns
	private Boolean turn = true;		//true when player turn, false when enemy turn
	private Boolean done = false;		//tells when the ui is done generating
	private Boolean ran = false;		//when true the battle is over
	private int tempDef;				//a defense that hold the players original defense
	
	private UIImageButton attack, defend, spell, item, ability, run;
	
	private boolean attackOccuring = false;
	private Animation attackAnimation;
	private boolean attackDone = false;
	
	private String dialog = " ";
	private String dialog2 = " ";
	
	private Timer timer;
	
	public BattleState(Handler handler)
	{
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		arena = new Arena(handler);
		handler.setArena(arena);
		
		bgmPlayer = handler.getBgmPlayer();
		
		//set up the timer to run for 5 seconds
		timer = new Timer(1000, null);
		timer.setRepeats(false);
	}
	
	public void tick()
	{
		arena.tick();
		player = handler.getArena().getPlayer();
		enemy = handler.getArena().getEnemy();
		
		if(!playerDone && !timer.isRunning())
		{
			handler.getMouseManager().setUIManager(uiManager);
			uiManager.tick();
		}
		else
			handler.getMouseManager().setUIManager(null);
		
		if(attackOccuring && !timer.isRunning())
		{
			attackAnimation.tick();
		}
		
		if(turn && !attackOccuring && !attackDone)
			playersTurn();
		
		if(playerDone && enemyDone && !handler.getArena().getBattleDone() && !attackOccuring && !attackDone)
		{
			if(player.isPoisonedStatus())
			{
				System.out.println(player.getName() + " is hurt by poison!");
				player.setCurrHP(player.getCurrHP() - 10);
			}
			player.setDefense(tempDef);
			turnStart = false;
		}
		
		if(!turnStart && !handler.getArena().getBattleDone() && !attackOccuring && !attackDone)
		{
			playerDone = false;
			enemyDone = false;
			turnGetter();
		}
	
	}
	
	public void render(Graphics g)
	{	
		arena.render(g);
		
		if(timer.isRunning() || !playerDone)
		{
			Color myColor = new Color(0, 0, 0, 127);
            
			g.drawRect(-1, 300, 501, 100);
			g.setColor(Color.white);
			g.drawRect(-1, 301, 501, 98);
			g.setColor(myColor);
			g.fillRect(0, 302, 500, 97);
			g.setColor(Color.white);
		}
		
		if(!playerDone && !timer.isRunning())
		{
			g.drawString("What do your do?", 200, 350);
			uiManager.render(g);
		}
		
		if(attackOccuring)
		{
			if(timer.isRunning())
			{
				g.drawString(dialog, 200, 350);
			}
		}
		
		//Display Attack Animation
		if(attackOccuring && !timer.isRunning())
		{
			g.drawImage(attackAnimation.getCurrentFrame(), 0, 0, 500, 500, null);
			
			if(attackAnimation.getIndex() == 5)
			{
				attackAnimation.stop();
				attackOccuring = false;
				attackDone = true;
				timer.restart();
			}
		}

		if(attackDone)
		{
			if(timer.isRunning() && (dialog2 != ""))
			{
				g.drawString(dialog2, 200, 350);
			}
			else
			{
				attackDone = false;
			}
		}
	}
	
	public void turnGetter()
	{
		turnStart = true;
		if(player.getSpeed() >= enemy.getSpeed())
		{
			turn = true;
			turns++;
			playersTurn();
		}
		else
		{
			turn = false;
			turns++;
			enemyTurn();
		}
			
	}
	
	public void playersTurn()
	{	
		if(!done)
			generateMenu();
		
		if(player.isParalyzeStatus())
		{
			playerDone = true;
			done = false;
		}
		
		checkHealth();
		
		if(!enemyDone && !handler.getArena().getBattleDone() && playerDone)
		{
			if(player.isParalyzeStatus())
			{
				player.setParalyzeStatus(false);
				dialog = player.getName() + " is no longer paralyzed!";
				dialog2 = "";
			}
			degenMenu();
			enemyTurn();
		}
	}
	
	public void enemyTurn()
	{
		enemy.battle();		//get enemies attack
		
		attackOccuring = true;
		attackAnimation = Assests.phAnimation;
		dialog = enemy.getDialog();
		
		if(player.getDefense() > enemy.getDamage())
		{
			//players defense is greater than damage, so no damage
			dialog2 = "Miss, no damage!";
		}
		else
		{
			attackOccuring = true;
			attackAnimation = Assests.enemyAttack;
			
			//calculate damage
			player.setCurrHP(player.getCurrHP() - (enemy.getDamage() - player.getDefense()));
			
			//output message
			dialog2 = enemy.getName() + " dealt " + (enemy.getDamage()  - player.getDefense()) + " damage!";
		}
		
		if(enemy.isParalyze() && !player.isParalyzeStatus())
		{
			dialog2 = player.getName() + " is paralyzed!";
			player.setParalyzeStatus(true);
		}
		
		if(enemy.isPoisoned() && !player.isPoisonedStatus())
		{
			dialog2 = player.getName() + " is poisoned!";
			player.setPoisonedStatus(true);
		}
		
		if(enemy.isDoNothing())
			dialog2 = "";
		
		timer.restart();
		
		enemyDone = true;
		checkHealth();
		if(!playerDone && !handler.getArena().getBattleDone())
			playersTurn();
		
	}
	
	public void checkHealth()
	{
		if(player.getCurrHP() <= 0)
		{
			player.setCurrHP(0);
			dialog = "You have died!";
			
			playerDone = false;
			enemyDone = false;
			turnStart = false;
			
			player.setCurrHP(1);
			player.setGold(player.getGold() / 2);
			
			handler.getArena().setBattleDone(true);
			handler.setBattleDone(true);
			handler.getArena().getBattleManager().getMonsters().remove(1);
			
			degenMenu();
			handler.getMouseManager().setUIManager(null);
			
			bgmPlayer.stopMusic();
			bgmPlayer.resumeMusic();
			
			handler.setBoss(false);
			
			State.setState(handler.getGame().gameState);
		}
		if(enemy.getCurrHP() <= 0)
		{
			bgmPlayer.playSound("/sound/sfx/victory.wav");
			dialog = "You have won!";
			
			playerDone = false;
			enemyDone = false;
			turnStart = false;
			
			player.setExp(player.getExp() + enemy.getXpDrop());
			player.setGold(player.getGold() + enemy.getGdrop());
					
			handler.getArena().setBattleDone(true);
			handler.setBattleDone(true);
			handler.getArena().getBattleManager().getMonsters().remove(1);
			
			degenMenu();
			handler.getMouseManager().setUIManager(null);
			
			bgmPlayer.stopMusic();
			bgmPlayer.resumeMusic();
			
			handler.setBoss(false);
			
			State.setState(handler.getGame().gameState);
		}
		if(ran)
		{
			dialog = "Ran Away!";
			
			playerDone = false;
			enemyDone = false;
			turnStart = false;
			ran = false;
			
			handler.getArena().setBattleDone(true);
			handler.setBattleDone(true);
			handler.getArena().getBattleManager().getMonsters().remove(1);
			
			degenMenu();
			handler.getMouseManager().setUIManager(null);
			
			bgmPlayer.stopMusic();
			bgmPlayer.resumeMusic();
			
			handler.setBoss(false);
			
			State.setState(handler.getGame().gameState);
		}
	}
	
	public void generateMenu()
	{
		generateUI();
		
		uiManager.addObject(attack);
		uiManager.addObject(defend);
		uiManager.addObject(item);
		uiManager.addObject(spell);
		uiManager.addObject(ability);
		uiManager.addObject(run);
		
		done = true;
	}
	
	public void degenMenu()
	{
		uiManager.removeObject(attack);
		uiManager.removeObject(defend);
		uiManager.removeObject(item);
		uiManager.removeObject(spell);
		uiManager.removeObject(ability);
		uiManager.removeObject(run);
	}
	
	public void generateUI()
	{
		attack = new UIImageButton(197, 398, 100, 50, Assests.attackBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				int damage = player.getAttack() + enemy.getDefense();
				
				enemy.setCurrHP(enemy.getCurrHP() - damage);
				
				attackOccuring = true;
				attackAnimation = Assests.normalAttack;
				dialog = player.getName() + " attacks!";
				dialog2 = player.getName() + " dealt " + damage + " damage!";
				
				timer.restart();
				playerDone = true;
				done = false;
			}}); 
		defend = new UIImageButton(298, 398, 100, 50, Assests.defendBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				dialog = player.getName() + " defends!";
				dialog2 = "";
				
				attackOccuring = true;
				attackAnimation = Assests.phAnimation;
				tempDef = player.getDefense();
				player.setDefense(player.getDefense() * 2);
				
				timer.restart();
				playerDone = true;
				done = false;
			}}); 
		item = new UIImageButton(399, 398, 100, 50, Assests.itemBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				
				timer.restart();
				playerDone = true;
				done = false;
			}}); 
		spell = new UIImageButton(197, 449, 100, 50, Assests.spellBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				
				timer.restart();
				playerDone = true;
				done = false;
			}}); 
		ability = new UIImageButton(298, 449, 100, 50, Assests.abilityBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				
				timer.restart();
				playerDone = true;
				done = false;
			}}); 
		run = new UIImageButton(399, 449, 100, 50, Assests.runBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				ran = true;
				
				timer.restart();
				playerDone = true;
				done = false;
			}}); 
	}
}
