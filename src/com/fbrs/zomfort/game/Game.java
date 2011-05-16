package com.fbrs.zomfort.game;

import java.util.HashMap;
import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.fbrs.zomfort.scripts.Physics;
import com.fbrs.zomfort.scripts.ZombieScript;

public class Game extends BaseGameActivity
{

	public static final int CAM_W = 800;
	public static final int CAM_H = 480;
	
	private Camera mCamera;
	private Texture[] mTexture;
	private TextureRegion[] mFaceTextureRegion;

	
	public static HashMap<String, TextureRegion> TexLookup;
	public static Scene scene;
	public static SSL ssl;
	//private PhysicsWorld mPhysicsWorld;

	
	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAM_W, CAM_H);
		TexLookup = new HashMap<String, TextureRegion>();
		ssl = new SSL();
		
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAM_W, CAM_H), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		
		int NUMTEXTURES = 5;
		int i = 0;
		
		mTexture = new Texture[NUMTEXTURES];
		mFaceTextureRegion = new TextureRegion[NUMTEXTURES];		
		
		this.mTexture[i] = new Texture(64, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mFaceTextureRegion[i] = TextureRegionFactory.createFromAsset(this.mTexture[i], this, "gfx/zombie.png", 0, 0);
        TexLookup.put("zombie", mFaceTextureRegion[i]);
        
        i++;
		this.mTexture[i] = new Texture(1024, 8, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mFaceTextureRegion[i] = TextureRegionFactory.createFromAsset(this.mTexture[i], this, "gfx/ground.png", 0, 0);
        TexLookup.put("ground", mFaceTextureRegion[i]);
        
        i++;
		this.mTexture[i] = new Texture(32, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mFaceTextureRegion[i] = TextureRegionFactory.createFromAsset(this.mTexture[i], this, "gfx/beam.png", 0, 0);
        TexLookup.put("beam", mFaceTextureRegion[i]);
        
        i++;
		this.mTexture[i] = new Texture(32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mFaceTextureRegion[i] = TextureRegionFactory.createFromAsset(this.mTexture[i], this, "gfx/dot.png", 0, 0);
        TexLookup.put("dot", mFaceTextureRegion[i]);


        for(int j = 0; j < i; j++)
        	this.mEngine.getTextureManager().loadTexture(this.mTexture[j]);
		
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		scene = new Scene(1);
		
		scene.setBackground(new ColorBackground(0, 0, 0.8f));
		
        /* Create the face and add it to the scene. */
/*
        final Sprite face = new Sprite(centerX, centerY, this.mFaceTextureRegion) {
                @Override
                public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                        this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
                        return true;
                }
        };
        face.setScale(4);
        scene.getLastChild().attachChild(face);
        scene.registerTouchArea(face);
        scene.setTouchAreaBindingEnabled(true);
*/
 
        //regesters the SSL
        scene.registerUpdateHandler(new IUpdateHandler(){

			@Override
			public void onUpdate(float pSecondsElapsed) {
				ssl.RunScripts();
			}

			@Override
			public void reset() {
				ssl.Clear();
			}
        	
        });
		
        //special regester for the physics
        scene.registerUpdateHandler(Physics.mPhysicsWorld);
  
        
		return scene;
	}
	

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		MakeGameObject("ground", new Vector2(0,480), ssl.CombineScripts("sSprite::sPhys"), BodyType.StaticBody);
		for(int i = 0; i < 10; i++)
		{
			//MakeGameObject("zombie", new Vector2(rand.nextInt(800),rand.nextInt(480)), ssl.CombineScripts("sZombie::sPhys"));
		}
		
		MakeGameObject("beam", new Vector2(CAM_W/2 - 16, CAM_H/2 - 128), ssl.CombineScripts("sBeam"));
		/*MakeGameObject("star", new Vector2(480,0), ssl.CombineScripts("sPhys::sZombie")); */
	}
	
	public static GameObject MakeGameObject(String Tex, Vector2 loc, IScript script)
	{
		GameObject newobj = new GameObject();
		newobj.bodType = BodyType.DynamicBody;
		newobj.loc = loc;
		newobj.Tex = Tex;
		newobj = (GameObject)script.ApplyScript(newobj);
		
		
        
        return newobj;
	}
	
	public static GameObject MakeGameObject(String Tex, Vector2 loc, IScript script, BodyType type)
	{
		GameObject newobj = new GameObject();
		newobj.bodType = type;
		newobj.loc = loc;
		newobj.Tex = Tex;
		newobj = (GameObject)script.ApplyScript(newobj);
		scene.registerTouchArea(newobj.sprite);
        return newobj;
	}
}
