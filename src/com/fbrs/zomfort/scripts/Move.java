package com.fbrs.zomfort.scripts;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.math.Vector2;
import com.fbrs.zomfort.game.Game;
import com.fbrs.zomfort.game.GameObject;
import com.fbrs.zomfort.game.IScript;

public class Move implements IScript {

	@Override
	public Object ApplyScript(Object o) {
		final GameObject obj = (GameObject)o; 
		
		final Sprite return_s = new Sprite(obj.loc.x, obj.loc.y, Game.TexLookup.get(obj.Tex)){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//test(this);
            this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
            //Vector2 oldpos = new Vector2(obj.Parent.sprite.getX(), obj.Parent.sprite.getY());
            obj.Parent.sprite.setPosition(pSceneTouchEvent.getX() - obj.Parent.sprite.getWidth() / 2, pSceneTouchEvent.getY() - obj.Parent.sprite.getHeight() / 2);
            Vector2 transform = new Vector2(obj.Parent.sprite.getX()+ obj.Parent.sprite.getWidth() / 2, obj.Parent.sprite.getY() + obj.Parent.sprite.getHeight() / 2);
            obj.Parent.body.setTransform(transform.mul(0.03125f), (float) Math.toRadians(obj.Parent.sprite.getRotation()));
            obj.Parent.circ1.sprite.setPosition(obj.Parent.sprite.getX(), obj.Parent.sprite.getY());
           
            
            return true;
			}
		};
		return_s.setScale(2);
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

	public void test(Sprite test)
	{
		Sprite s = test;
		int i = 0;
	}
}
