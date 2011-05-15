package com.fbrs.Math;

public class Vector2 {

	public int x, y;
	
	public Vector2()
	{
		x = 0;
		y = 0;
	}
	
	public Vector2(int i)
	{
		x = i;
		y = i;
	}
	
	public Vector2(int i, int j)
	{
		x = i;
		y = j;
	}
	
	public Vector2 add(Vector2 v2)
	{
		v2.x += x;
		v2.y += y;
		
		return v2;
	}
	
	
}
