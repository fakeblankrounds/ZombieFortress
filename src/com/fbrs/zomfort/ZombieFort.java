package com.fbrs.zomfort;

import com.fbrs.zomfort.game.Game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ZombieFort extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        Intent intent = new Intent(this.getBaseContext(), Game.class );
        this.startActivity(intent);
    }
}