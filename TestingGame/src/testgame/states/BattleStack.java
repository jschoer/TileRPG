package testgame.states;

import java.util.ArrayList;

import testgame.battle.monsters.Monster;

public class BattleStack<T, T2, T3, T4, T5, T6, T7>
{
	public Node<T, T2, T3, T4, T5, T6, T7> top;	//makes top node
	
	/*public Stack()	//set the  and top
	{
		top = null;	//not needed?
	}
	*/
	public void push(T m, T2 damage, T3 dialog, T4 dialog2, T5 speed, T6 attacker, T7 animation)	//push the element to the top
	{
		Node<T, T2, T3, T4, T5, T6, T7> newNode = new Node<T, T2, T3, T4, T5, T6, T7>(m, damage, dialog, dialog2, speed, attacker, animation);	//makes a new node
		
		newNode.setTarget(m);	//sets the new nodes info
		newNode.setDamage(damage);
		newNode.setDialog(dialog);
		newNode.setDialog2(dialog2);
		newNode.setSpeed(speed);
		newNode.setAttacker(attacker);
		newNode.setAnimation(animation);
		
		newNode.setLink(top);		//sets its link
		
		top = newNode;				//top is now the new node
	}
	
	public void pop() throws StackUnderFlowException	//take the top of the stack and push it
	{
		if (isEmpty() != true)	//if stack is not empty
		{
			top = top.getLink();	//get the link
		}
		else
			throw new StackUnderFlowException("Pop attempted on an empty stack.");	//if the stack is empty stop
	}
	
	public T getTop() throws StackUnderFlowException	//return the top node
	{
		if(!isEmpty())
		{
			return (T) top.getTarget();	//would not get info properly without the cast
		}
		else 
			throw new StackUnderFlowException("Top attempted on an empty stack.");	//if stack is empty stop
	}

	public boolean isEmpty()	//checks to see if the stack is empty
	{
		if(top == null)	//if top is null
		{
			return true;	//that means stack is empty
		}
		else return false;	//if not it has nodes
	}
}
