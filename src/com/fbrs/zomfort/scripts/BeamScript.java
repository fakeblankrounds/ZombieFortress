package com.fbrs.zomfort.scripts;

import java.util.ArrayList;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.Sprite;

import com.badlogic.gdx.math.Vector2;
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
		
		
		beam.circ1 = Game.MakeGameObject("dot", beam.loc.add(0, 0), Game.ssl.CombineScripts("sRotate"));
		beam.circ1.Parent = beam;
		beam.circ2 = Game.MakeGameObject("dot",new Vector2(return_s.getX() + (return_s.getWidth()/2) - 16, return_s.getY() + (return_s.getHeight()/2) - 16), Game.ssl.CombineScripts("sMove"));
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
