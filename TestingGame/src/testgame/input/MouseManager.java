package testgame.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import testgame.battle.BattleManager;
import testgame.battle.monsters.Monster;
import testgame.entities.EntityManager;
import testgame.ui.UIManager;

public class MouseManager implements MouseListener, MouseMotionListener
{
	private boolean leftPressed, wheelPressed, rightPressed;
	private int xPos, yPos;
	
	private UIManager uiManager;
	private EntityManager entityManager;
	private BattleManager battleManager;
	
	public void setUIManager(UIManager uiManager)
	{
		this.uiManager = uiManager;
	}
	
	public void setEntityManager(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public void setBattleManager(BattleManager battleManager)
	{
		this.battleManager = battleManager;
	}
	
	public boolean isLeftPressed()
	{
		return leftPressed;
	}
	
	public boolean isWheelPressed()
	{
		return wheelPressed;
	}
	
	public boolean isRightPressed()
	{
		return rightPressed;
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		xPos = e.getX();
		yPos = e.getY();
		
		if(uiManager != null)
			uiManager.onMouseMove(e);
		if(entityManager != null)
			entityManager.onMouseMove(e);
		if(battleManager != null)
			battleManager.onMouseMove(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = true;
		else if(e.getButton() == MouseEvent.BUTTON2)
			wheelPressed = true;
		else if(e.getButton() == MouseEvent.BUTTON3)
			rightPressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = false;
		else if(e.getButton() == MouseEvent.BUTTON2)
			wheelPressed = false;
		else if(e.getButton() == MouseEvent.BUTTON3)
			rightPressed = false;
		
		if(uiManager != null)
			uiManager.onMouseRelease(e);
		if(entityManager != null)
			entityManager.onMouseRelease(e);
		if(battleManager != null)
			battleManager.onMouseRelease(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}
	
	public int getMouseX()
	{
		return xPos;
	}
	
	public int getMouseY()
	{
		return yPos;
	}
}
