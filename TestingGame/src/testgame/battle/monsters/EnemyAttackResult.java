package testgame.battle.monsters;

import java.util.Random;

public class EnemyAttackResult
{
	private Random randomGenerator = new Random();
	
	public int RNG(int x)
	{
		return randomGenerator.nextInt(x);
	}
	
	public int normalAttack(int attack)
	{
		
		return (RNG(2) + attack + 1);
	}
	
	public int tackleAttack(int attack)
	{
		return (RNG(5) + attack + 5);
	}
	
	public int slamAttack(int attack)
	{
		return (RNG(10) + attack + 10);
	}
	
	public int fireBall(int intelligence)
	{
		return (RNG(2) + intelligence + 3);
	}
}
