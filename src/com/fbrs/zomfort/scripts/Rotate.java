package com.fbrs.zomfort.scripts;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

import com.fbrs.zomfort.game.Game;
import com.fbrs.zomfort.game.GameObject;
import com.fbrs.zomfort.game.IScript;

public class Rotate implements IScript{

	@Override
	public Object ApplyScript(Object o) {
		final GameObject obj = (GameObject)o; 
		
		final Sprite return_s = new Sprite(obj.loc.x, obj.loc.y, Game.TexLookup.get(obj.Tex)){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
           //this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
            obj.Parent.sprite.setRotation((float) Math.atan(pSceneTouchEvent.getX()/pSceneTouchEvent.getY()));
            this.setPosition((float)Math.cos(obj.Parent.sprite.getRotation() * 2),(float) Math.sin(obj.Parent.sprite.getRotation() * 2));
            return true;
			}
		};
		
		final PhysicsHandler physicsHandler = new PhysicsHandler(return_s);
		return_s.registerUpdateHandler(physicsHandler);
		Game.scene.getLastChild().attachChild(return_s);
		Game.scene.registerTouchArea(obj.sprite);
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
