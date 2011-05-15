package com.fbrs.zomfort.scripts;

import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.shape.RectangularShape;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.fbrs.zomfort.game.Game;
import com.fbrs.zomfort.game.GameObject;
import com.fbrs.zomfort.game.IScript;

public class Physics implements IScript {

	public static PhysicsWorld mPhysicsWorld;
	private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
	
	public Physics()
	{
		//Physics.mPhysicsWorld =
		mPhysicsWorld = new PhysicsWorld(new Vector2(0, 9), false);
		//Rectangle ground = new Rectangle(0, Game.CAM_H - 2, Game.CAM_W, 2);
        //Shape roof = new Rectangle(0, 0, Game.CAM_W, 2);
        //Shape left = new Rectangle(0, 0, 2, Game.CAM_H);
        //Shape right = new Rectangle(Game.CAM_W - 2, 0, 2, Game.CAM_H);

        final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
        PhysicsFactory.createBoxBody(mPhysicsWorld, new Rectangle(0, Game.CAM_H - 2, Game.CAM_W, 2), BodyType.StaticBody, wallFixtureDef);
        //PhysicsFactory.createBoxBody(mPhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
        //PhysicsFactory.createBoxBody(mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef);
        //PhysicsFactory.createBoxBody(mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef);

	
	}
	@Override
	public void ApplyScript(Object o) {
		// TODO Auto-generated method stub
		GameObject phys = (GameObject)o;
		
		phys.body = PhysicsFactory.createBoxBody(mPhysicsWorld, phys.sprite, BodyType.DynamicBody, FIXTURE_DEF);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(phys.sprite, phys.body, true, true));
	}

	@Override
	public void RunScript() {
		// TODO Auto-generated method stub
		//mPhysicsWorld.onUpdate(pSecondsElapsed);
	}

	@Override
	public void Clear() {
		// TODO Auto-generated method stub

	}

}
