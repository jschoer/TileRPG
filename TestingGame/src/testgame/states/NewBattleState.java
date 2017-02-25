package testgame.states;

import java.awt.Graphics;
import java.util.Random;

import testgame.Handler;
import testgame.battle.monsters.Monster;
import testgame.gfx.Animation;
import testgame.gfx.Assests;
import testgame.sound.BGMplayer;
import testgame.ui.ClickListener;
import testgame.ui.UIImageButton;
import testgame.ui.UIManager;
import testgame.worlds.NewArena;

public class NewBattleState extends State
{
	private NewArena newArena;
	private long lastClick, clickCooldown = 250, clickTimer = 0;
	
	private int index = 0;
	private boolean[] levels = new boolean[100];
	
	private UIManager uiManager;
	private UIImageButton attack, defend, spell, item, ability, run;
	
	private BGMplayer bgmPlayer;
	
	private BattleStack<Monster, Integer, String, String, Integer, Monster, Animation> battleStack = new BattleStack<Monster, Integer, String, String, Integer, Monster, Animation>();
	private boolean playerDone = false, enemyDone = false, commandSelected = false, menuCreated = false, canceled, battlestarted = false,
			ran = false, expDone = false, dialog1done = false, animation = false, dialog2done = false, attackDone = false, sorted = false, dead = false;

	public NewBattleState(Handler handler)
	{
		super(handler);
		
		uiManager = new UIManager(handler);
		bgmPlayer = handler.getBgmPlayer();
		
		handler.setTarget(-1);
	}

	@Override
	public void tick() throws StackUnderFlowException
	{
		clickTimer += System.currentTimeMillis() - lastClick;
		lastClick = System.currentTimeMillis();
		
		//If the arena is null get the newly created one by GameState
		if(newArena == null)
		{
			newArena = handler.getNewArena();
			handler.getNewArena().setDialog("The enemy approaches.");
		}
		if((!menuCreated && handler.getMouseManager().isLeftPressed()) || (!menuCreated && battlestarted))
		{
			battlestarted = true;
			handler.getNewArena().setDialog("empty");
			handler.setSelecting(false);
			generateMenu();
		}
		if(commandSelected)
		{
			handler.getNewArena().setDialog("Select a target. Right click to cancel.");
			canceled = true;
			handler.setSelecting(true);
			degenMenu();
		}
		if(handler.getMouseManager().isRightPressed() && canceled)
		{
			handler.getNewArena().setDialog("empty");
			canceled = false;
			handler.setSelecting(false);
			generateMenu();
			commandSelected = false;
		}
		
		newArena.tick();
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
		
		//When all enemies are defeated end the battle or when you die... or ran.
		if((handler.getNewArena().getBattleManager().getMonsters().isEmpty() || handler.getNewArena().getPlayer().getCurrHP() <= 0 || ran) && !playerDone && !enemyDone)
		{
			degenMenu();
			//Check Exp and levels
			if(handler.getNewArena().getPlayer().getCurrHP() > 0 && !ran)
			{
				handler.getNewArena().setDialog("The enemy is defeated!");
				if(!expDone)
				{
					handler.getNewArena().getPlayer().setExp(handler.getNewArena().getPlayer().getExp() + handler.getNewArena().getExpDropped());
					expDone = true;
				}
				
				while((handler.getNewArena().getPlayer().getExp() >= (10 * (index + 1))) && (index < 99))
				{
					levelUp();
				}
			}
			else  if(!ran)
				handler.getNewArena().setDialog("The party is wiped.");
			else if(ran)
				handler.getNewArena().setDialog("Ran away!");
			
			bgmPlayer.stopMusic();
			
			if(handler.getMouseManager().isLeftPressed() && clickTimer >= clickCooldown)
			{
				if(handler.getNewArena().getPlayer().getCurrHP() <= 0)
					handler.getNewArena().getPlayer().setCurrHP(1);
				
				handler.setBoss(false);
				handler.getMouseManager().setUIManager(null);
				newArena = null;
				handler.getMouseManager().setBattleManager(null);
				handler.getNewArena().setDialog("empty");
				
				expDone = false;
				ran = false;
				battlestarted = false;
				
				bgmPlayer.resumeMusic();
				State.setState(handler.getGame().gameState);
			}
		}
		
		//Work in progress as of now can only attack
		if(handler.getTarget() != -1 && commandSelected)
		{
			for(Monster m : handler.getNewArena().getBattleManager().getMonsters())
				if(m.getTarget() == handler.getTarget())
				{
					int damage = handler.getNewArena().getPlayer().getAttack() - m.getDefense();
					String dialog = handler.getNewArena().getPlayer().getName() + " attacks!";
					String dialog2 = handler.getNewArena().getPlayer().getName() + " deals " + damage + " damage!";
					
					battleStack.push(m, damage, dialog, dialog2, handler.getNewArena().getPlayer().getSpeed(), handler.getNewArena().getPlayer(), Assests.normalAttack);
					playerDone = true;
					handler.getNewArena().setDialog("empty");
					enemyTurn();
				}
			commandSelected = false;
			handler.setSelecting(false);
			handler.setTarget(-1);
		}
		else
			handler.setTarget(-1);
		
		//Determine stack order then start the battle!
		if(playerDone && enemyDone)
		{
			if(!sorted)
			{
				sortStack(battleStack);
				sorted = true;
			}
			degenMenu();
			
			while(!battleStack.isEmpty())
			{
				//check to see if anyone is dead
				for(Monster m : handler.getNewArena().getBattleManager().getMonsters())
					if(m == battleStack.top.getAttacker())
					{
						if(m.getCurrHP() <= 0)
							dead = true;
					}
					else if(battleStack.top.getAttacker() == handler.getNewArena().getPlayer())
					{
						if(handler.getNewArena().getPlayer().getCurrHP() <= 0)
							dead = true;
					}
						
				//perform the action
				if(!dead)
				{
					if(!dialog1done)
						handler.getNewArena().setDialog(battleStack.top.getDialog());
					if(!dialog1done && handler.getMouseManager().isLeftPressed() && clickTimer >= clickCooldown)
					{
						clickTimer = 0;
						dialog1done = true;
					}
					
					if(dialog1done && !animation)
					{
						handler.getNewArena().setAnimation(battleStack.top.getAnimation());
						handler.getNewArena().setAnimate(true);
						animation = true;
					}
					
					if(dialog1done && !attackDone && animation)
					{
						attackDone = true;
						battleStack.top.getTarget().setCurrHP(battleStack.top.getTarget().getCurrHP() - battleStack.top.getDamage());
					}
					
					if(battleStack.top.getDialog2() != "empty" && dialog1done && !dialog2done)
						handler.getNewArena().setDialog(battleStack.top.getDialog2());
					else if(dialog1done && !dialog2done)
					{
						handler.getNewArena().setDialog("empty");
						dialog2done = true;
					}
					if(!dialog2done && handler.getMouseManager().isLeftPressed() && clickTimer >= clickCooldown)
					{
						clickTimer = 0;
						dialog2done = true;
					}
				}
				else
				{
					dialog1done = true;
					dialog2done = true;
				}
			
				if(dialog1done && dialog2done)
				{
					handler.getNewArena().setDialog("empty");
					attackDone = false;
					dialog1done = false;
					dialog2done = false;
					dead = false;
					animation = false;
					battleStack.pop();
				}
				else
					break;
			}
			
			if(battleStack.isEmpty())
			{
				handler.getNewArena().setDialog("empty");
				handler.getNewArena().update();
				sorted = false;
				playerDone = false;
				enemyDone = false;
			}
		}
	}

	@Override
	public void render(Graphics g)
	{
		newArena.render(g);
		uiManager.render(g);
	}
	
	public void enemyTurn()
	{
		for(Monster m : handler.getNewArena().getBattleManager().getMonsters())
		{
			m.battle();
			int damage = 0;
			if(m.getDamage() <= handler.getNewArena().getPlayer().getDefense())
			{
				if(!m.isDoNothing())
					m.setDialog2("The attack missed!");
				else
					m.setDialog2("empty");
			}
			else
			{
				damage = m.getDamage() - handler.getNewArena().getPlayer().getDefense();
				m.setDialog2(m.getName() + " deals " + damage + " damage!");
			}
			
			battleStack.push(handler.getNewArena().getPlayer(), damage, m.getDialog(), m.getDialog2(), m.getSpeed(), m, m.getAnimation());
		}
		enemyDone = true;
	}
	
	//public BattleStack sortStack(Battlestack stack)
	//Takes in a stack that needs to be organized
	//organizes the stack by the speed of the attackers
	public BattleStack<Monster, Integer, String, String, Integer, Monster, Animation> sortStack(BattleStack<Monster, Integer, String, String, Integer, Monster, Animation> battleStack) throws StackUnderFlowException
	{
		if(battleStack.isEmpty())
			return battleStack;
		Monster m = battleStack.top.getTarget();
		int damage = battleStack.top.getDamage();
		String dialog = battleStack.top.getDialog();
		String dialog2 = battleStack.top.getDialog2();
		int speed = battleStack.top.getSpeed();
		Monster a = battleStack.top.getAttacker();
		Animation anim = battleStack.top.getAnimation();
		battleStack.pop();
		
		BattleStack<Monster, Integer, String, String, Integer, Monster, Animation> left = new BattleStack<Monster, Integer, String, String, Integer, Monster, Animation>();
		BattleStack<Monster, Integer, String, String, Integer, Monster, Animation> right = new BattleStack<Monster, Integer, String, String, Integer, Monster, Animation>();
		
		while(!battleStack.isEmpty())
		{
			Monster n = battleStack.top.getTarget();
			int damage2 = battleStack.top.getDamage();
			String newdialog = battleStack.top.getDialog();
			String newdialog2 = battleStack.top.getDialog2();
			int speed2 = battleStack.top.getSpeed();
			Monster a2 = battleStack.top.getAttacker();
			Animation anim2 = battleStack.top.getAnimation();
			battleStack.pop();
			
	        if(speed2 < speed)
	        	left.push(n, damage2, newdialog, newdialog2, speed2, a2, anim2);
	        else
	        	right.push(n, damage2, newdialog, newdialog2, speed2, a2, anim2);
	     }
		sortStack(left);
	    sortStack(right);
	    
	    BattleStack<Monster, Integer, String, String, Integer, Monster, Animation> temp = new BattleStack<Monster, Integer, String, String, Integer, Monster, Animation>();
	    while(!right.isEmpty())
	    {
	        temp.push(right.top.getTarget(), right.top.getDamage(), right.top.getDialog(), right.top.getDialog2(), right.top.getSpeed(), right.top.getAttacker(), right.top.getAnimation());
	        right.pop();
	    }
	    temp.push(m, damage, dialog, dialog2, speed, a, anim);
	    while(!left.isEmpty())
	    {
	        temp.push(left.top.getTarget(), left.top.getDamage(), left.top.getDialog(), left.top.getDialog2(), left.top.getSpeed(), left.top.getAttacker(), left.top.getAnimation());
	        left.pop();
	    }
	    while(!temp.isEmpty())
	    {
	    	battleStack.push(temp.top.getTarget(), temp.top.getDamage(), temp.top.getDialog(), temp.top.getDialog2(), temp.top.getSpeed(), temp.top.getAttacker(), temp.top.getAnimation());
	    	temp.pop();
	    }
	    
		return battleStack;
	}
	
	//Level up yo
	public void levelUp()
	{
		int increment;
		Random random = new Random();
		
		handler.getNewArena().getPlayer().setLevel(handler.getNewArena().getPlayer().getLevel() + 1);
		
		increment = random.nextInt(2) + 1;
		handler.getNewArena().getPlayer().setStrength(handler.getNewArena().getPlayer().getStrength() + increment);
		
		increment = random.nextInt(1) + 1;
		handler.getNewArena().getPlayer().setResilience(handler.getNewArena().getPlayer().getResilience() + increment);
		
		increment = random.nextInt(1) + 1;
		handler.getNewArena().getPlayer().setIntelligence(handler.getNewArena().getPlayer().getIntelligence() + increment);
		
		increment = random.nextInt(5);
		handler.getNewArena().getPlayer().setMagic(handler.getNewArena().getPlayer().getMagic() + increment);
		
		increment = random.nextInt(3);
		handler.getNewArena().getPlayer().setSpeed(handler.getNewArena().getPlayer().getSpeed() + increment);
		
		increment = random.nextInt(10) + 2;
		handler.getNewArena().getPlayer().setHealth(handler.getNewArena().getPlayer().getHealth() + increment);
		
		handler.getNewArena().getPlayer().setCurrHP(handler.getNewArena().getPlayer().getHealth());
		handler.getNewArena().getPlayer().setCurrMP(handler.getNewArena().getPlayer().getMagic());
		
		levels[index] = true;
		index++;
		
		handler.getNewArena().getPlayer().tick();
	}
	
	public void generateMenu()
	{
		generateUI();
		
		uiManager.addObject(attack);
		uiManager.addObject(run);
		
		menuCreated = true;
	}
	
	public void degenMenu()
	{
		uiManager.removeObject(attack);
		uiManager.removeObject(run);
		
		menuCreated = false;
	}
	
	public void generateUI()
	{
		attack = new UIImageButton(197, 398, 100, 50, Assests.attackBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				commandSelected = true;
			}});
		run = new UIImageButton(399, 449, 100, 50, Assests.runBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				ran = true;
			}}); 
	}

}
