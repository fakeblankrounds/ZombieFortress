package com.fbrs.zomfort.game;

import org.anddev.andengine.entity.sprite.Sprite;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Beam extends GameObject{
	

	public Sprite sprite;
	public Body body;
	
	public BodyType bodType;
	public Vector2 loc;
	public String Tex;
	
	public GameObject circ1;
	public GameObject circ2;
	public GameObject circ3;

}
