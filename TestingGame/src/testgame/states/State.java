package testgame.states;

import java.awt.Graphics;

import testgame.Game;
import testgame.Handler;

public abstract class State
{
	private static State currentState = null;
	
	public static void setState(State state)
	{
		currentState = state;
	}
	
	public static State getState()
	{
		return currentState;
	}
	
	protected Handler handler;
	
	public State(Handler handler)
	{
		this.handler = handler;
	}
	
	public abstract void tick() throws StackUnderFlowException;
	
	public abstract void render(Graphics g);
	
}
