package com.fbrs.zomfort.scripts;

import java.util.ArrayList;

import org.anddev.andengine.entity.sprite.Sprite;

import com.fbrs.zomfort.game.IScript;

public class ZombieScript implements IScript {

	ArrayList<Sprite> zombies;
	
	public ZombieScript()
	{
		zombies = new ArrayList<Sprite>();
	}
	
	@Override
	public void ApplyScript(Object o) {
		Sprite add = (Sprite) o;

	}

	@Override
	public void RunScript() {
		

	}

	@Override
	public void Clear() {
		zombies.clear();
	}

}
