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

	public static final int CAM_W = 800 * 2;
	public static final int CAM_H = 480 * 2;
	
	private Camera mCamera;
	private Texture[] mTexture;
	private TextureRegion[] mFaceTextureRegion;
	private int TexCount = 0;

	
	public static HashMap<String, TextureRegion> TexLookup;
	public static Scene scene;
	public static SSL ssl;
	
	public static GameObject ground;
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
		
		int NUMTEXTURES = 10;
		
		mTexture = new Texture[NUMTEXTURES];
		mFaceTextureRegion = new TextureRegion[NUMTEXTURES];	
		
		addPng("zombie", 64, 128);
		addPng("ground", 1024, 8);
		addPng("beam", 32, 256);
		addPng("dot", 32,32);
		addPng("go", 128,128);
		addPng("spawn", 128, 128);
		addPng("build", 128, 128);
		addPng("bg", 1024,512);
		addPng("rollinghills", 256,256);
		addPng("brain", 128,128);

        for(int j = 0; j < TexCount; j++)
        	this.mEngine.getTextureManager().loadTexture(this.mTexture[j]);
		
	}
	
	public void addPng(String png, int sizeX, int sizeY)
	{
		
			this.mTexture[TexCount] = new Texture(sizeX, sizeY, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	        this.mFaceTextureRegion[TexCount] = TextureRegionFactory.createFromAsset(this.mTexture[TexCount], this, "gfx/" + png +".png", 0, 0);
	        //this.mFaceTextureRegion[TexCount].
	        TexLookup.put(png, mFaceTextureRegion[TexCount]);
	        TexCount++;
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		scene = new Scene(1);
		
		scene.setBackground(new ColorBackground(0, 0, 0.8f));
		 
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
		ground = MakeGameObject("ground", new Vector2(CAM_W/4,CAM_H), ssl.CombineScripts("sSprite::sPhys"), BodyType.StaticBody);
		ground.spriteType = 5;
		GameObject bg = MakeGameObject("bg", new Vector2(400,240), ssl.CombineScripts("sSprite"));
		GameObject foreground = MakeGameObject("rollinghills", new Vector2(0, CAM_H*0.75f), ssl.CombineScripts("sSprite"));
		foreground.sprite.setZIndex(3);
		bg.sprite.setZIndex(0);
		for(int i = 0; i < 10; i++)
		{
			//MakeGameObject("zombie", new Vector2(rand.nextInt(800),rand.nextInt(480)), ssl.CombineScripts("sZombie::sPhys"));
		}		
		//MakeGameObject("beam", new Vector2(CAM_W/2 - 16, CAM_H/2 - 128), ssl.CombineScripts("sBeam::sPhys"), BodyType.StaticBody);
		
		MakeGameObject("", new Vector2(0,0), ssl.CombineScripts("sGameStates")).sprite.setZIndex(10);
		
		MakeGameObject("",new Vector2(0,128), ssl.CombineScripts("sBeamSpawner")).sprite.setZIndex(10);
		
		MakeGameObject("brain", new Vector2(CAM_W * 0.75f, CAM_H * 0.75f), ssl.CombineScripts("sBeam::sBrain::sPhys"), BodyType.StaticBody);

	
	}
	
	public static GameObject MakeGameObject(String Tex, Vector2 loc, IScript script)
	{
		GameObject newobj = new GameObject();
		newobj.bodType = BodyType.DynamicBody;
		newobj.loc = loc;
		newobj.Tex = Tex;
		newobj = (GameObject)script.ApplyScript(newobj);
		Game.scene.getFirstChild().sortChildren();
        return newobj;
	}
	
	public static GameObject MakeGameObject(String Tex, Vector2 loc, IScript script, BodyType type)
	{
		GameObject newobj = new GameObject();
		newobj.bodType = type;
		newobj.loc = loc;
		newobj.Tex = Tex;
		newobj = (GameObject)script.ApplyScript(newobj);
		Game.scene.getFirstChild().sortChildren();
        return newobj;
	}
}
