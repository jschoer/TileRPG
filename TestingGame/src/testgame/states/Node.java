package testgame.states;

public class Node<T, T2, T3, T4, T5, T6, T7>
{
	private T target;
	private T2 damage;
	private T3 dialog;
	private T4 dialog2;
	private T5 speed;
	private T6 attacker;
	private T7 animation;
	
	@SuppressWarnings("rawtypes")
	private Node link;
	
	public Node(T target, T2 damage, T3 dialog, T4 dialog2, T5 speed, T6 attacker, T7 animation)
	{
		this.target = target;
		this.damage = damage;
		this.dialog = dialog;
		this.dialog2 = dialog2;
		this.speed = speed;
		this.attacker = attacker;
		this.animation = animation;
		
		link = null;
	}
	
	public T getTarget()
	{
		return target;
	}

	public void setTarget(T target)
	{
		this.target = target;
	}

	public T2 getDamage()
	{
		return damage;
	}

	public void setDamage(T2 damage)
	{
		this.damage = damage;
	}

	public T3 getDialog()
	{
		return dialog;
	}

	public void setDialog(T3 dialog)
	{
		this.dialog = dialog;
	}

	public T4 getDialog2()
	{
		return dialog2;
	}

	public void setDialog2(T4 dialog2)
	{
		this.dialog2 = dialog2;
	}

	public T5 getSpeed()
	{
		return speed;
	}

	public void setSpeed(T5 speed)
	{
		this.speed = speed;
	}

	public T6 getAttacker()
	{
		return attacker;
	}

	public void setAttacker(T6 attacker)
	{
		this.attacker = attacker;
	}
	
	public T7 getAnimation()
	{
		return animation;
	}
	
	public void setAnimation(T7 animation)
	{
		this.animation = animation;
	}

	@SuppressWarnings("rawtypes")
	public void setLink(Node link)
	{
		this.link = link;
	}
	
	@SuppressWarnings("rawtypes")
	public Node getLink()
	{
		return link;
	}
}
