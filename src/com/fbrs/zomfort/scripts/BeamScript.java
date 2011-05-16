package com.fbrs.zomfort.scripts;

import java.util.ArrayList;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.Sprite;

import com.fbrs.zomfort.game.Beam;
import com.fbrs.zomfort.game.Game;
import com.fbrs.zomfort.game.GameObject;
import com.fbrs.zomfort.game.IScript;

public class BeamScript implements IScript {

	ArrayList<GameObject> beams;
	
	public BeamScript()
	{
		beams = new ArrayList<GameObject>();
	}
	
	@Override
	public Object ApplyScript(Object o) {
		GameObject beam = (GameObject)o;
		
		final Sprite return_s = new Sprite(beam.loc.x, beam.loc.y, Game.TexLookup.get(beam.Tex));
		final PhysicsHandler physicsHandler = new PhysicsHandler(return_s);
		return_s.registerUpdateHandler(physicsHandler);
		Game.scene.getLastChild().attachChild(return_s);
		//Game.scene.registerTouchArea(return_s);
        //Game.scene.setTouchAreaBindingEnabled(true);
		beam.sprite = return_s;
		
		
		//beam.circ1 = Game.MakeGameObject("dot", beam.loc.add(0, 120), Game.ssl.CombineScripts("sRotate"));
		beam.circ2 = Game.MakeGameObject("dot", beam.loc.sub(beam.loc.mul(0.5f)), Game.ssl.CombineScripts("sMove"));
		beam.circ2.Parent = beam;
		beams.add(beam);
		return beam;	
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
