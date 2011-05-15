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
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.fbrs.Math.Vector2;
import com.fbrs.zomfort.scripts.Physics;

public class Game extends BaseGameActivity
{

	public static final int CAM_W = 800;
	public static final int CAM_H = 480;
	
	private Camera mCamera;
	private Texture mTexture;
	private TextureRegion mFaceTextureRegion;
	private Texture mTexture2;
	private TextureRegion mFaceTextureRegion2;
	
	private HashMap<String, TextureRegion> TexLookup;
	private Scene scene;
	private SSL ssl;
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
		this.mTexture = new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mFaceTextureRegion = TextureRegionFactory.createFromAsset(this.mTexture, this, "gfx/star.png", 0, 0);
        TexLookup.put("star", mFaceTextureRegion);
        
		this.mTexture2 = new Texture(1024, 8, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mFaceTextureRegion2 = TextureRegionFactory.createFromAsset(this.mTexture2, this, "gfx/ground.png", 0, 0);
        TexLookup.put("ground", mFaceTextureRegion2);

        this.mEngine.getTextureManager().loadTexture(this.mTexture);
		
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		scene = new Scene(1);
		scene.setBackground(new ColorBackground(0, 0, 0.8f));
		
		/* Calculate the coordinates for the face, so its centered on the camera. */
        final int centerX = (CAM_W - this.mFaceTextureRegion.getWidth()) / 2;
        final int centerY = (CAM_H - this.mFaceTextureRegion.getHeight()) / 2;

        /* Create the face and add it to the scene. */
        final Sprite face = new Sprite(centerX, centerY, this.mFaceTextureRegion);
        scene.getLastChild().attachChild(face);
        
        final PhysicsHandler physicsHandler = new PhysicsHandler(face);
        face.registerUpdateHandler(physicsHandler);

        scene.getLastChild().attachChild(face);

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
		MakeGameObject("ground", new Vector2(0,480), ssl.CombineScripts("sPhys"), BodyType.StaticBody);
		for(int i = 0; i < 100; i++)
		{
			MakeGameObject("star", new Vector2(rand.nextInt(800),rand.nextInt(480)), ssl.CombineScripts("sPhys::sZombie"));
		}
	}
	
	public GameObject MakeGameObject(String Tex, Vector2 loc, IScript script)
	{
		Sprite return_s = new Sprite(loc.x, loc.y, TexLookup.get(Tex));
		final PhysicsHandler physicsHandler = new PhysicsHandler(return_s);
        return_s.registerUpdateHandler(physicsHandler);
        scene.getLastChild().attachChild(return_s);
        GameObject newobj = new GameObject();
        newobj.sprite = return_s;
        newobj.bodType = BodyType.DynamicBody;
        script.ApplyScript(newobj);
        
        return newobj;
	}
	
	public GameObject MakeGameObject(String Tex, Vector2 loc, IScript script, BodyType type)
	{
		Sprite return_s = new Sprite(loc.x, loc.y, TexLookup.get(Tex));
		final PhysicsHandler physicsHandler = new PhysicsHandler(return_s);
        return_s.registerUpdateHandler(physicsHandler);
        scene.getLastChild().attachChild(return_s);
        GameObject newobj = new GameObject();
        newobj.sprite = return_s;
        newobj.bodType = type;
        	
        script.ApplyScript(newobj);
        
        return newobj;
	}
}
