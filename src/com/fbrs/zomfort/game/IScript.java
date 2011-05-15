package com.fbrs.zomfort.game;

public interface IScript {
	
	//This should return the edited object. 
	public Object ApplyScript(Object o);
	
	public void RunScript();
	
	public void Clear();

}
