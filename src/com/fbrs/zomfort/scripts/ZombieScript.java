package com.fbrs.zomfort.scripts;

import java.util.ArrayList;

import org.anddev.andengine.entity.sprite.Sprite;

import com.fbrs.zomfort.game.GameObject;
import com.fbrs.zomfort.game.IScript;

public class ZombieScript implements IScript {

	ArrayList<GameObject> zombies;
	
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
		

	}

	@Override
	public void Clear() {
		zombies.clear();
	}

}