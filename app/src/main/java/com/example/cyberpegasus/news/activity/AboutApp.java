package com.example.cyberpegasus.news.activity;

/**
 * Created by Cyber Pegasus on 9/17/2018.
 */
import java.util.Calendar;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.cyberpegasus.news.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.TranslateAnimation;
        import android.widget.RelativeLayout;

        import com.example.cyberpegasus.news.R;

public class AboutApp extends AppBaseActivity {

    Animation anim1,anim2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutapp);
        moveViewToScreenCenter(findViewById(R.id.names));
        moveViewToScreenCenter(findViewById(R.id.detail));
        moveViewToScreenCenter(findViewById(R.id.campus));
        moveIcon(findViewById(R.id.imageViewAbout));
        moveIcon(findViewById(R.id.title));
    }
    private void moveIcon( View view )
    {
        int originalPos[] = new int[2];
        view.getLocationOnScreen(originalPos);

        anim2 = new TranslateAnimation( 0, 0, 0, originalPos[1]+50 );
        anim2.setDuration(2000);
        anim2.setFillAfter(true);
        view.startAnimation(anim2);
    }

    private void moveViewToScreenCenter( View view )
    {
        RelativeLayout root = (RelativeLayout) findViewById( R.id.ctr );
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

        int originalPos[] = new int[2];
        view.getLocationOnScreen(originalPos);

        int xDest = dm.widthPixels/2;
        xDest -= (view.getMeasuredWidth()/2);
        int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;

        anim1 = new TranslateAnimation( 0, 0, 0, yDest - originalPos[1]+750 );
        anim1.setDuration(2500);
        anim1.setFillAfter(true);
        view.startAnimation(anim1);
    }
}


