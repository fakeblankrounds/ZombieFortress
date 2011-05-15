package com.fbrs.zomfort.scripts;

import java.util.ArrayList;

import com.fbrs.zomfort.game.Beam;
import com.fbrs.zomfort.game.Game;
import com.fbrs.zomfort.game.IScript;

public class BeamScript implements IScript {

	ArrayList<Beam> beams;
	
	public BeamScript()
	{
		beams = new ArrayList<Beam>();
	}
	
	@Override
	public Object ApplyScript(Object o) {
		Beam beam = (Beam)o;
		
		beams.add(beam);
		
		beam.circ1 = Game.MakeGameObject("dot", beam.loc.add(0, 120), Game.ssl.CombineScripts("sRotate"))beam;
		beam.circ2 = Game.MakeGameObject("dot", beam.loc.sub(0, 120), Game.ssl.CombineScripts("sMove"));
		beam.circ2.Parent = beam;
		
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
