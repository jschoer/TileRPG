package testgame.entities.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import testgame.Handler;
import testgame.entities.ClickListener;
import testgame.gfx.Animation;
import testgame.gfx.Assests;

public class Slime extends Creature
{
	private Animation aniDown, aniUp, aniLeft, aniRight;
	Animation[] allAnimations = new Animation[4];
	private int right = 0;
	private int up = 0;
	private int left = 0;
	private int down = 0;
	private int movement;
	private int index = 0;
	private ClickListener clicked;
	
	public Slime(Handler handler, float x, float y, int width, int height, int movement, ClickListener clicked)
	{
		super(handler, x, y, Creature.default_creature_width, Creature.default_creature_height);	
		this.movement = movement;
		this.clicked = clicked;
		
		bounds.x = 0;
		bounds.y = 45;
		bounds.width = 64;
		bounds.height = 11;
		
		aniDown = new Animation(200, Assests.slime_down);
		aniUp = new Animation(200, Assests.slime_up);
		aniLeft = new Animation(200, Assests.slime_left);
		aniRight = new Animation(200 ,Assests.slime_right);
		
		allAnimations[0] = aniDown;
		allAnimations[1] = aniUp;
		allAnimations[2] = aniLeft;
		allAnimations[3] = aniRight;
	}

	@Override
	public void tick()
	{
		aniDown.tick();
		aniLeft.tick();
		aniRight.tick();
		aniUp.tick();

		allAnimations[0].tick();
		allAnimations[1].tick();
		allAnimations[2].tick();
		allAnimations[3].tick();
		
		if(!handler.isTalking())
			getInput();
		move();
	}
	
	private void getInput()
	{
		xMove = 0;
		yMove = 0;
		if(movement == 0)
		{
			if(right <= 100 && down == 0 && left == 0 && up == 0)
			{
				xMove = speed;
				right++;
			}
			if(down <= 100 && right == 100 && left == 0 && up == 0)
			{
				yMove = speed;
				down++;
			}
			if(left <= 100 && down == 100 && right == 100 && up == 0)
			{
				xMove = -speed;
				left++;
			}
			if(up <= 100 && left == 100 && right == 100 && down == 100)
			{
				yMove = -speed;
				up++;
			}
			if(up == 100)
			{
				up = 0;
				right = 0;
				left = 0;
				down = 0;
			}
		}
		else if(movement == 1)
		{
			xMove = speed;
			yMove = speed;
		
			if((int)(Math.random()*10) > 5)
			{
				xMove = -xMove;
			}
			if((int)(Math.random()*10) > 5)
			{
				yMove = -yMove;
			}
		}
		else if(movement == 2)
		{
			if(handler.getKeyManager().up)
				yMove = -speed;
			if(handler.getKeyManager().down)
				yMove = speed;
			if(handler.getKeyManager().left)
				xMove = -speed;
			if(handler.getKeyManager().right)
				xMove = speed;
			if(handler.getKeyManager().run)
				speed = 20;
			else
				speed = default_speed;
		}
		else
		{
			xMove = 0;
			yMove = 0;
		}
	}

	@Override
	public void render(Graphics g)
	{
		if(hovering)
			g.fillRect((int)(x + clickBounds.x - handler.getGameCamera().getxOffset()), (int)(y + clickBounds.y - handler.getGameCamera().getyOffset()), clickBounds.width, clickBounds.height);
		g.drawImage(getCurrentAnimation(), (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()),
			    width, height, null);
		//g.drawImage(getCurrentAnimation(), (int)x, (int)y, width, height, null);
	}
	
	private BufferedImage getCurrentAnimation()
	{
		if(xMove < 0)
		{
			index = 2;
			//return aniLeft.getCurrentFrame();
		}
		else if(xMove > 0)
		{
			index = 3;
			//return aniRight.getCurrentFrame();
		}
		else if(yMove < 0)
		{
			index = 1;
			//return aniUp.getCurrentFrame();
		}
		else if(yMove > 0)
		{
			index = 0;
			//return aniDown.getCurrentFrame();
		}
		return allAnimations[index].getCurrentFrame();
		
	}

	@Override
	public void onClick()
	{
		clicked.OnClick();
		
	}

}
