package com.fbrs.zomfort.scripts;

import com.fbrs.zomfort.game.GameObject;
import com.fbrs.zomfort.game.IScript;

public class BrainScript implements IScript {

	public static GameObject brain;
	
	@Override
	public Object ApplyScript(Object o) {
		// TODO Auto-generated method stub
		GameObject obj = (GameObject)o;
		brain = obj;
		brain.spriteType = 7;
		return obj;
	}

	@Override
	public void RunScript() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Clear() {
		// TODO Auto-generated method stub

	}

}
