package com.fbrs.zomfort.game;

import java.util.HashMap;

import com.fbrs.zomfort.scripts.AddSprite;
import com.fbrs.zomfort.scripts.BeamScript;
import com.fbrs.zomfort.scripts.BeamSpawner;
import com.fbrs.zomfort.scripts.BrainScript;
import com.fbrs.zomfort.scripts.GameStates;
import com.fbrs.zomfort.scripts.Move;
import com.fbrs.zomfort.scripts.Physics;
import com.fbrs.zomfort.scripts.Rotate;
import com.fbrs.zomfort.scripts.ZombieScript;

public class SSL {

	public HashMap<String, IScript> scriptsMap;
	
	public SSL()
	{
		scriptsMap = new HashMap<String, IScript>();
		
		scriptsMap.put("sZombie", new ZombieScript());
		scriptsMap.put("sPhys", new Physics());
		scriptsMap.put("sSprite", new AddSprite());
		scriptsMap.put("sRotate", new Rotate());
		scriptsMap.put("sMove", new Move());
		scriptsMap.put("sBeam", new BeamScript());
		scriptsMap.put("sGameStates", new GameStates());
		scriptsMap.put("sBeamSpawner", new BeamSpawner());
		scriptsMap.put("sBrain", new BrainScript());
	}
	
	public void RunScripts()
	{
		for(IScript script: scriptsMap.values())
		{
			script.RunScript();
		}
	}
	
	public void Clear()
	{
		for(IScript script : scriptsMap.values())
		{
			script.Clear();
		}
	}
	
	public IScript CombineScripts(String scripts)
	{
		String[] substring = scripts.split("::");
		
		final IScript[] apply = new IScript[substring.length];
		
		for(int i = 0; i < substring.length; i++)
		{
			apply[i] = scriptsMap.get(substring[i]);
		}

		return new IScript(){

			@Override
			public Object ApplyScript(Object o) {
				Object obj = o;
				for(int i = 0; i < apply.length; i++)
				{
					obj = apply[i].ApplyScript(obj);
				}
				
				return obj;
			}

			@Override
			public void RunScript() {
				//dont do anything here. This script will never be ran.
			}

			@Override
			public void Clear() {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		
	}
}
