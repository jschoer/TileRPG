package testgame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import testgame.Handler;
import testgame.battle.BattleManager;
import testgame.battle.monsters.Dummy;
import testgame.entities.ClickListener;
import testgame.gfx.Animation;
import testgame.gfx.Assests;
import testgame.sound.BGMplayer;
import testgame.states.State;
import testgame.worlds.Arena;

public class Player extends Creature
{
	//Animations
	private Animation aniDown, aniUp, aniLeft, aniRight, aniIdle, aniAttack, textBubble;
	public boolean walking = false;
	private ClickListener clicked;
	
	public Player(Handler handler, float x, float y, ClickListener clicked)
	{
		super(handler, x, y, Creature.default_creature_width, Creature.default_creature_height);
		
		handler.setPlayer(this);
		
		this.clicked = clicked;
		
		bounds.x = 12;
		bounds.y = 40;
		bounds.width= 32;
		bounds.height = 18;
		
		aniDown = new Animation(200, Assests.player_down);
		aniUp = new Animation(200, Assests.player_up);
		aniLeft = new Animation(200, Assests.player_left);
		aniRight = new Animation(200 ,Assests.player_right);
		aniIdle = new Animation(200, Assests.player_idle);
		aniAttack = new Animation(150, Assests.player_attack);
		textBubble = new Animation(400, Assests.textBubble);
	}

	@Override
	public void tick()
	{
		//animation
		aniDown.tick();
		aniLeft.tick();
		aniRight.tick();
		aniUp.tick();
		aniIdle.tick();
		aniAttack.tick();
		textBubble.tick();
		
		//move
		if(!handler.isTalking())
		{
			getInput();
			move();
		}
		
		handler.getGameCamera().centerOnEntity(this);
	}
	
	private void getInput()
	{
		xMove = 0;
		yMove = 0;
		
		if(handler.getKeyManager().up)
		{
			yMove = -speed;
			walking = true;
		}
		if(handler.getKeyManager().down)
		{
			yMove = speed;
			walking = true;
		}
		if(handler.getKeyManager().left)
		{
			xMove = -speed;
			walking = true;
		}
		if(handler.getKeyManager().right)
		{
			xMove = speed;
			walking = true;
		}
		if(handler.getKeyManager().run)
			setSpeed(6);
		else
			setSpeed(default_speed);
	}

	@Override
	public void render(Graphics g)
	{
		Color myColor = new Color(0, 0, 0, 50);
		g.setColor(myColor);
		//if(hovering)
			//g.fillRect((int)(x + clickBounds.x - handler.getGameCamera().getxOffset()), (int)(y + clickBounds.y - handler.getGameCamera().getyOffset()), clickBounds.width, clickBounds.height);
		g.drawImage(getCurrentAnimation(), (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()),
				    width, height, null);
		if(handler.isTalking())
			g.drawImage(textBubble.getCurrentFrame(), (int)(x + bounds.x - handler.getGameCamera().getxOffset()) + 10, (int)(y + bounds.y - handler.getGameCamera().getyOffset()) - 60, 32, 32, null);
	}
	
	private BufferedImage getCurrentAnimation()
	{
		if(xMove < 0)
		{
			return aniLeft.getCurrentFrame();
		}
		else if(xMove > 0)
		{
			return aniRight.getCurrentFrame();
		}
		else if(yMove < 0)
		{
			return aniUp.getCurrentFrame();
		}
		else if(yMove > 0)
		{
			return aniDown.getCurrentFrame();
		}
		else
			return aniIdle.getCurrentFrame();
	}
	
	public void setWalking(boolean walking)
	{
		this.walking = walking;
	}
	
	public boolean isWalking()
	{
		return walking;
	}

	@Override
	public void onClick()
	{
		clicked.OnClick();
		
	}
}
