package com.fbrs.zomfort.scripts;

import java.util.ArrayList;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.math.Vector2;
import com.fbrs.zomfort.game.Game;
import com.fbrs.zomfort.game.GameObject;
import com.fbrs.zomfort.game.IScript;

public class ZombieScript implements IScript {

	ArrayList<GameObject> zombies;
	
	Vector2 brain = new Vector2(80,80);
	
	int framecount = 0;
	
	public ZombieScript()
	{
		zombies = new ArrayList<GameObject>();
	}
	
	@Override
	public Object ApplyScript(Object o) {
		GameObject obj = (GameObject)o;
		zombies.add(obj);
		final Sprite return_s = new Sprite(obj.loc.x, obj.loc.y, Game.TexLookup.get(obj.Tex));
		final PhysicsHandler physicsHandler = new PhysicsHandler(return_s);
		return_s.registerUpdateHandler(physicsHandler);
		Game.scene.getLastChild().attachChild(return_s);
		//Game.scene.registerTouchArea(return_s);
        //Game.scene.setTouchAreaBindingEnabled(true);
		obj.sprite = return_s;
		Game.scene.registerTouchArea(obj.sprite);
		return obj;

	}

	@Override
	public void RunScript() {
		if(framecount == 10)
		{
			for(GameObject zombie : zombies)
			{
				zombie.body.setAngularDamping(100);
				zombie.body.applyLinearImpulse(brain.sub(zombie.body.getPosition()).nor().mul(10), zombie.body.getLocalCenter());
			}
			framecount = 0;
		}
		else
			framecount++;

	}

	@Override
	public void Clear() {
		zombies.clear();
	}
	
}
