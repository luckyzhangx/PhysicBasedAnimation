package com.example.luckyzhangx.physicbasedanimation;

import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    View action, action2, action3, action4, action5;
    float initialX, initialY;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        action = findViewById(R.id.action);
        action2 = findViewById(R.id.action2);
        action3 = findViewById(R.id.action3);
        action4 = findViewById(R.id.action4);
        action5 = findViewById(R.id.action5);

        final SpringAnimation x = new SpringAnimation(action, DynamicAnimation.TRANSLATION_X, 0);
        final SpringAnimation y = new SpringAnimation(action, DynamicAnimation.TRANSLATION_Y, 0);

        springFactory(x);
        springFactory(y);

        SpringAnimation[] temp = {x, y};

        addTranslateAnimation(temp, action2);
        addTranslateAnimation(temp, action3);
        addTranslateAnimation(temp, action4);
        addTranslateAnimation(temp, action5);

        ((View) action.getParent()).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x.animateToFinalPosition(event.getX() - action.getWidth() / 2);
                y.animateToFinalPosition(event.getY() - action.getHeight() / 2);
                return true;
            }
        });
    }

    private void springFactory(SpringAnimation springAnimation) {
        springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
        springAnimation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
    }

    private SpringAnimation[] addTranslateAnimation(SpringAnimation[] followee, View view) {
        if (followee == null || followee.length != 2)
            return null;

        final SpringAnimation x = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X, 0);
        final SpringAnimation y = new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y, 0);

        springFactory(x);
        springFactory(y);

        addSpring(followee[0], x);
        addSpring(followee[1], y);

        followee[0] = x;
        followee[1] = y;

        return followee;
    }

    private void addSpring(SpringAnimation origin, final SpringAnimation follow) {
        origin.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                follow.animateToFinalPosition(value);
            }
        });

    }


}
