package com.fbrs.zomfort.scripts;

import java.util.ArrayList;

import org.anddev.andengine.entity.sprite.Sprite;

import com.badlogic.gdx.math.Vector2;
import com.fbrs.zomfort.game.GameObject;
import com.fbrs.zomfort.game.IScript;

public class ZombieScript implements IScript {

	ArrayList<GameObject> zombies;
	
	Vector2 brain = new Vector2(80,80);
	
	public ZombieScript()
	{
		zombies = new ArrayList<GameObject>();
	}
	
	@Override
	public void ApplyScript(Object o) {
		zombies.add((GameObject) o);

	}

	@Override
	public void RunScript() {
		for(GameObject zombie : zombies)
		{
			zombie.body.applyLinearImpulse(brain.sub(zombie.body.getPosition()).nor(), zombie.body.getLocalCenter());
		}

	}

	@Override
	public void Clear() {
		zombies.clear();
	}

}
