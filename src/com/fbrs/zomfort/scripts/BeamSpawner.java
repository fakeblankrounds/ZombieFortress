package com.fbrs.zomfort.scripts;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.fbrs.zomfort.game.Game;
import com.fbrs.zomfort.game.GameObject;
import com.fbrs.zomfort.game.IScript;

public class BeamSpawner implements IScript {

	public static boolean cantouch = true;
	
	@Override
	public Object ApplyScript(Object o) {
		GameObject obj = (GameObject)o;
		final Sprite return_s  = new Sprite(obj.loc.x, obj.loc.y, Game.TexLookup.get("spawn")){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown() && cantouch){
				GameStates.buildstate = true;
				
				Game.MakeGameObject("beam", new Vector2(Game.CAM_W/2, Game.CAM_H/2 ), Game.ssl.CombineScripts("sBeam::sPhys"), BodyType.StaticBody);
				
				cantouch = false; 
				}
				else if(pSceneTouchEvent.isActionUp())
				{
					cantouch = true;
				}
				
            return true;
			}
		};
		Game.scene.getLastChild().attachChild(return_s);
		Game.scene.registerTouchArea(return_s);
		obj.sprite = return_s;
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
