package com.fbrs.zomfort.scripts;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.math.Vector2;
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
			obj.Parent.sprite.setRotation((float) Math.toDegrees(Math.atan(pSceneTouchEvent.getY()/pSceneTouchEvent.getX())));
            //this.setPosition((float)obj.Parent.sprite.getX(),(float) obj.Parent.sprite.getY());
            this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
            
            Vector2 transform = new Vector2(obj.Parent.sprite.getX()+ obj.Parent.sprite.getWidth() / 2, obj.Parent.sprite.getY() + obj.Parent.sprite.getHeight() / 2);
            obj.Parent.body.setTransform(transform.mul(0.03125f), obj.Parent.sprite.getRotation());
				return true;
			}
		};
		
		//final PhysicsHandler physicsHandler = new PhysicsHandler(return_s);
		//return_s.registerUpdateHandler(physicsHandler);
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
