package com.fbrs.zomfort.scripts;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.math.Vector2;
import com.fbrs.zomfort.game.Game;
import com.fbrs.zomfort.game.GameObject;
import com.fbrs.zomfort.game.IScript;

public class GameStates implements IScript {

	
	public static int waveNumber;
	public static boolean buildstate = true;
	public static Sprite  goSprite;
	public static Sprite  buildSprite;
	public static boolean cantouch = true;
	public static int zombieCount;
	private static int framecount = 0;
	
	
	@Override
	public Object ApplyScript(Object o) {
		//we apply this to the "Go" button.
		waveNumber = 5;
final GameObject obj = (GameObject)o; 
		
		buildSprite = new Sprite(obj.loc.x, obj.loc.y, Game.TexLookup.get("build")){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown() && cantouch){
				GameStates.buildstate = false;
				obj.sprite = goSprite;
				Game.scene.registerTouchArea(goSprite);
				Game.scene.unregisterTouchArea(buildSprite);
				Game.scene.getLastChild().attachChild(goSprite);
				cantouch = false;
				zombieCount = 0;
				}
				else if(pSceneTouchEvent.isActionUp())
				{
					cantouch = true;
				}
				//Game.scene.getLastChild().detachChild(buildSprite);
            return true;
			}
			
			//@Override
		};
		//
		//Game.scene.registerTouchArea(buildSprite);
		//obj.sprite = buildSprite;
		
		goSprite = new Sprite(obj.loc.x, obj.loc.y, Game.TexLookup.get("go")){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown() && cantouch){
				GameStates.buildstate = true;
				obj.sprite = buildSprite;
				Game.scene.registerTouchArea(buildSprite);
				Game.scene.unregisterTouchArea(goSprite);
				Game.scene.getLastChild().attachChild(buildSprite);
				///Game.scene.getLastChild().detachChild(goSprite);
				}
				else if(pSceneTouchEvent.isActionUp())
				{
					cantouch = true;
				}
				
            return true;
			}
		};
		Game.scene.getLastChild().attachChild(buildSprite);
		Game.scene.registerTouchArea(buildSprite);
		obj.sprite = buildSprite;
		
		
		return obj;
	}

	@Override
	public void RunScript() {
		if(!buildstate)
		{
			if(framecount >= 20)
			{
				if(zombieCount < waveNumber * waveNumber)
				{
					//spawn 3 more zombies
					Game.MakeGameObject("zombie", new Vector2(10,-10), Game.ssl.CombineScripts("sZombie::sPhys"));
					zombieCount++;
				}
				framecount = 0;
			}
			else
				framecount++;
		}

	}

	@Override
	public void Clear() {
		// TODO Auto-generated method stub

	}

}
