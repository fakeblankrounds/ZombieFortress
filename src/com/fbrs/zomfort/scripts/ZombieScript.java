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
	
	Vector2 brain = new Vector2(0,0);
	
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
		return_s.setScale(0.5f);
		return_s.setZIndex(1);
		obj.sprite = return_s;
		Game.scene.registerTouchArea(obj.sprite);
		obj.spriteType = 3;
		
		obj.Health = 5;
		return obj;

	}

	@Override
	public void RunScript() {
		if(framecount == 10)
		{
			brain.set(BrainScript.brain.sprite.getX(), BrainScript.brain.sprite.getX());
			for(GameObject zombie : zombies)
			{
				if(zombie.Health > 0 )
				{
					if(zombie.collisionList.contains(Game.ground)){
					zombie.body.setAngularDamping(100);
					zombie.body.applyLinearImpulse(brain.nor().mul(10), zombie.body.getLocalCenter());
					//zombie.Health--;
					}
					for(int i = 0; i < zombie.collisionList.size(); i++)
					{
						if(zombie.collisionList.get(i).spriteType == 4)
						{
							zombie.collisionList.get(i).Health--;
							zombie.Health--;
						}
					}
				}
				else
				{
					zombie.body.setAngularDamping(0.2f);
					zombie.body.setAwake(false);
				}
			}//zombie.body.getPosition()
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
