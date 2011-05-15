package com.fbrs.zomfort.game;

import java.util.HashMap;

import com.fbrs.zomfort.scripts.Physics;
import com.fbrs.zomfort.scripts.ZombieScript;

public class SSL {

	public HashMap<String, IScript> scriptsMap;
	
	public SSL()
	{
		scriptsMap = new HashMap<String, IScript>();
		
		scriptsMap.put("sZombie", new ZombieScript());
		Physics phys = new Physics();
		scriptsMap.put("sPhys", phys);
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
			public void ApplyScript(Object o) {
				for(int i = 0; i < apply.length; i++)
				{
					apply[i].ApplyScript(o);
				}
				
				
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
