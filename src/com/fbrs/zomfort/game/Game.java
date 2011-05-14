package com.fbrs.zomfort.game;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class Game extends BaseGameActivity
{

	private static final int CAM_W = 800;
	private static final int CAM_H = 480;
	
	private Camera mCamera;
	private Texture mTexture;
	private TextureRegion mFaceTextureRegion;
	
	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAM_W, CAM_H);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAM_W, CAM_H), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		this.mTexture = new Texture(32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mFaceTextureRegion = TextureRegionFactory.createFromAsset(this.mTexture, this, "gfx/star.png", 0, 0);

        this.mEngine.getTextureManager().loadTexture(this.mTexture);
		
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		final Scene scene = new Scene(1);
		scene.setBackground(new ColorBackground(0, 0, 0.8f));
		
		/* Calculate the coordinates for the face, so its centered on the camera. */
        final int centerX = (CAM_W - this.mFaceTextureRegion.getWidth()) / 2;
        final int centerY = (CAM_H - this.mFaceTextureRegion.getHeight()) / 2;

        /* Create the face and add it to the scene. */
        final Sprite face = new Sprite(centerX, centerY, this.mFaceTextureRegion);
        scene.getLastChild().attachChild(face);

		
		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
	
}
