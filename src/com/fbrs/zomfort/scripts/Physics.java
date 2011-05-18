package com.fbrs.zomfort.scripts;

import java.util.ArrayList;

import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.shape.RectangularShape;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.fbrs.zomfort.game.Game;
import com.fbrs.zomfort.game.GameObject;
import com.fbrs.zomfort.game.IScript;

public class Physics implements IScript {

	public static PhysicsWorld mPhysicsWorld;
	private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
	
	public Physics()
	{
		mPhysicsWorld = new PhysicsWorld(new Vector2(0, 9), false);
		mPhysicsWorld.setContactListener(new ContactListener(){

		@Override
		public void beginContact(Contact contact) {
			((GameObject)(contact.getFixtureA().getBody().getUserData())).collisionList.add((GameObject)contact.getFixtureB().getBody().getUserData());
			((GameObject)(contact.getFixtureB().getBody().getUserData())).collisionList.add((GameObject)contact.getFixtureA().getBody().getUserData());
		}

		@Override
		public void endContact(Contact contact) {
			((GameObject)(contact.getFixtureA().getBody().getUserData())).collisionList.remove((GameObject)contact.getFixtureB().getBody().getUserData());
			((GameObject)(contact.getFixtureB().getBody().getUserData())).collisionList.remove((GameObject)contact.getFixtureA().getBody().getUserData());
			
		}
		});

        final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
     

	
	}
	@Override
	public Object ApplyScript(Object o) {
		// TODO Auto-generated method stub
		GameObject phys = (GameObject)o;
		
		phys.body = PhysicsFactory.createBoxBody(mPhysicsWorld, phys.sprite, phys.bodType, FIXTURE_DEF);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(phys.sprite, phys.body, true, true));
		phys.body.setUserData(phys);
		phys.collisionList = new ArrayList<GameObject>();
		
		return phys;
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
