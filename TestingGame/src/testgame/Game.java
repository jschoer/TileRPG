package testgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import testgame.gfx.Assests;
import testgame.gfx.GameCamera;
import testgame.gfx.ImageLoader;
import testgame.gfx.SpriteSheet;
import testgame.input.KeyManager;
import testgame.input.MouseManager;
import testgame.sound.BGMplayer;
import testgame.states.BattleState;
import testgame.states.GameState;
import testgame.states.MenuState;
import testgame.states.NewBattleState;
import testgame.states.StackUnderFlowException;
import testgame.states.State;

public class Game implements Runnable
{
	private Display display;
	public String title;
	private int width, height;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//States
	public State gameState;
	public State menuState;
	public State battleState;
	public State newBattleState;
	
	//Inputs
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	//Camera
	private GameCamera gameCamera;
	
	//Sound
	private BGMplayer bgmPlayer;
	
	//Handler
	private Handler handler;
	
	
	public Game(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		
	}
	
	private void init()
	{
		display = new Display(title,width,height);
		
		display.getFrame().addKeyListener(keyManager);
		
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		Assests.init();
		
		handler = new Handler(this);
		
		gameCamera = new GameCamera(handler, 0, 0);
		bgmPlayer = new BGMplayer(handler);
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		battleState = new BattleState(handler);
		newBattleState = new NewBattleState(handler);
		
		State.setState(menuState);
		
	}
	
	private void tick() throws StackUnderFlowException
	{
		keyManager.tick();
		
		if(State.getState() != null)
		{
			State.getState().tick();
		}
	}
	
	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);	//clears screen
		handler.setG(g);
		//draw
		
		if(State.getState() != null)
		{
			State.getState().render(g);
		}
		
		//stop draw
		bs.show();
		g.dispose();
	}

	public void run()
	{
		init();
		
		int fps  = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running)
		{
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1)
			{
				try
				{
					tick();
				} catch (StackUnderFlowException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000)
			{
				//System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
	}
	
	public KeyManager getKeyManager()
	{
		return keyManager;
	}
	
	public MouseManager getMouseManager()
	{
		return mouseManager;
	}
	
	public GameCamera getGameCamera()
	{
		return gameCamera;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public synchronized void start()
	{
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(!running)
			return;
		running = false;
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
