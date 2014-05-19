package mobi.tomo.whack_a_mole;


import java.sql.Types;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.*;

public class SplashActivity extends Activity {

  private TweenManager tweenManager = new TweenManager();
  private boolean isAnimationRunning = true;
  private LinearLayout genueHamster;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
	// タイトルを非表示にします。
	requestWindowFeature(Window.FEATURE_NO_TITLE);

	setContentView(R.layout.splash);

    //Setup it
    genueHamster = (LinearLayout) findViewById(R.id.main_cont);
    setTweenEngine();
  }

  /**
   * Initiate the Tween Engine
   */
  private void setTweenEngine() {
    //start animation theread
    setAnimationThread();
    
    //**Register Accessor, this is very important to do!
    //You need register actually each Accessor, but right now we have global one, which actually suitable for everything.
    Tween.registerAccessor(ViewContainer.class, new ViewContainerAccessor());
    
    startAnimation();
    
  }

  /**
   * Timeout 1 sec after press
 * @param ROTATION 
 * @param COMPLETE 
   */
  public void startAnimation() {

    //Create object which we will animate
    ViewContainer cont = new ViewContainer();
    TweenCallback callBack = new TweenCallback(){
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			// TODO Auto-generated method stub
            Log.v("tag222",String.valueOf(arg0));
            startTitle();
		}
    };
    
    //pass our real container
    cont.view = genueHamster;
    
    Timeline.createSequence()
    /*
     *   public static final int POSITION_X = 1;
     *   public static final int POSITION_Y = 2;
     *   public static final int POSITION_XY = 3;
     *   public static final int ROTATION = 4;
     *   public static final int SCALE = 5;
     *   public static final int ALPHA = 6;
     */
    .push(Tween.set(cont, 6).target(0))
    .push(Tween.to(cont, 6, 2.0f).target(1).ease(Back.OUT).delay(0.5f))
    .push(Tween.set(cont, 6).target(1))
    .push(Tween.to(cont, 4, 2.0f).target(360).ease(Bounce.OUT))
 	.push(Tween.to(cont, 1, 1.0f).target(-200).ease(Expo.OUT).delay(0.5f))
 	.push(Tween.to(cont, 1, 1.5f).target(3000).ease(Expo.OUT))
    .push(Tween.call(callBack))
    .start(tweenManager); 
  }

public void startTitle() {
		// スプラッシュ完了後に実行するActivityを指定します。
		Intent intent = new Intent(getApplication(), TitleActivity.class);
		startActivity(intent);
		// SplashActivityを終了させます。
		SplashActivity.this.finish();
  }

  /***
   * Thread that should run for update UI via Tween engine
   */
  private void setAnimationThread() {

    new Thread(new Runnable() {
      private long lastMillis = -1;

      @Override public void run() {
        while (isAnimationRunning) {
          if (lastMillis > 0) {
            long currentMillis = System.currentTimeMillis();
            final float delta = (currentMillis - lastMillis) / 1000f;

            /**
             * We run all animation in UI thread instead of using post for each elements.
             */
            runOnUiThread(new Runnable() {

              @Override public void run() {
                tweenManager.update(delta);

              }
            });

            lastMillis = currentMillis;
          } else {
            lastMillis = System.currentTimeMillis();
          }

          try {
            Thread.sleep(1000 / 60);
          } catch (InterruptedException ex) {
          }
        }
      }
    }).start();

  }

  /**
   * Stop animation thread
   */
  private void setAnimationFalse() {
    isAnimationRunning = false;
  }

  /**
   * Make animation thread alive
   */
  private void setAnimationTrue() {
    isAnimationRunning = true;
  }

}
