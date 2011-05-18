package com.fbrs.zomfort.game;

import java.util.ArrayList;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;

public class GameObject extends Object{

	public Sprite sprite;
	public Body body;
	
	
	public BodyType bodType;
	public Vector2 loc;
	public String Tex;
	//public Fixture fix;
	/*
	 * Sprite Types: 
	 * 0 = Scenery - should not collide
	 * 1 = Gui - should not collide
	 * 2 = Sensors - should collide, not drawn
	 * 3+ = everything else
	 */
	public int spriteType;
	
	public ArrayList<GameObject> collisionList;
	
	public GameObject Parent;
	public GameObject circ1;
	public GameObject circ2;
	public GameObject circ3;
	
	public int Health;
}
