package testgame;

import java.awt.Graphics;

import testgame.entities.creatures.Player;
import testgame.gfx.GameCamera;
import testgame.input.KeyManager;
import testgame.input.MouseManager;
import testgame.sound.BGMplayer;
import testgame.worlds.Arena;
import testgame.worlds.NewArena;
import testgame.worlds.World;

public class Handler
{
	private Game game;
	private World world;
	private Arena arena;
	private NewArena newArena;
	
	private Player player;
	private String yourName;
	
	private BGMplayer bgmPlayer;
	
	private int localIndex;
	
	private boolean talking = false;
	private String[] dialog;
	private int dialogLength;
	
	private boolean boss;
	private boolean battleDone;
	
	//New Battle Related stuff
	private int target;
	private boolean selecting;
	private Graphics g;
	
	public Handler(Game game)
	{
		this.game = game;
	}
	
	public int getWidth()
	{
		return game.getWidth();
	}
	
	public int getHeight()
	{
		return game.getHeight();
	}
	
	public KeyManager getKeyManager()
	{
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager()
	{
		return game.getMouseManager();
	}
	
	public GameCamera getGameCamera()
	{
		return game.getGameCamera();
	}

	public Game getGame()
	{
		return game;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}

	public World getWorld()
	{
		return world;
	}

	public void setWorld(World world)
	{
		this.world = world;
	}
	
	public Arena getArena()
	{
		return arena;
	}

	public void setArena(Arena arena)
	{
		this.arena = arena;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return player;
	}

	public BGMplayer getBgmPlayer()
	{
		return bgmPlayer;
	}

	public void setBgmPlayer(BGMplayer bgmPlayer)
	{
		this.bgmPlayer = bgmPlayer;
	}

	public int getLocalIndex()
	{
		return localIndex;
	}

	public void setLocalIndex(int localIndex)
	{
		this.localIndex = localIndex;
	}

	public boolean isTalking()
	{
		return talking;
	}

	public void setTalking(boolean talking)
	{
		this.talking = talking;
	}

	public String[] getDialog()
	{
		return dialog;
	}

	public void setDialog(String[] dialog)
	{
		this.dialog = dialog;
	}

	public int getDialogLength()
	{
		return dialogLength;
	}

	public void setDialogLength(int dialogLength)
	{
		this.dialogLength = dialogLength;
	}

	public boolean isBattleDone()
	{
		return battleDone;
	}
	

	public void setBattleDone(boolean battleDone)
	{
		this.battleDone = battleDone;
	}

	public boolean isBoss()
	{
		return boss;
	}

	public void setBoss(boolean boss)
	{
		this.boss = boss;
	}

	public String getYourName()
	{
		return yourName;
	}

	public void setYourName(String yourName)
	{
		this.yourName = yourName;
	}

	public NewArena getNewArena()
	{
		return newArena;
	}

	public void setNewArena(NewArena newArena)
	{
		this.newArena = newArena;
	}

	public int getTarget()
	{
		return target;
	}

	public void setTarget(int target)
	{
		this.target = target;
	}

	public boolean isSelecting()
	{
		return selecting;
	}

	public void setSelecting(boolean selecting)
	{
		this.selecting = selecting;
	}

	public Graphics getG()
	{
		return g;
	}

	public void setG(Graphics g)
	{
		this.g = g;
	}
	
	
	
}
