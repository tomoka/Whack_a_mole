package mobi.tomo.whack_a_mole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends Activity implements AnimationListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// タイトルを非表示にします。
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// splash.xmlをViewに指定します。
		setContentView(R.layout.splash);
		
		ImageView img = (ImageView) findViewById(R.id.img);

		//スプラッシュアニメーションのセット
		 AlphaAnimation alphaanime = new AlphaAnimation(1, 0);
		 alphaanime.setStartOffset(1000);
		 alphaanime.setDuration(3000);
		 alphaanime.setFillAfter(true);
		 alphaanime.setAnimationListener(this);
		 img.startAnimation(alphaanime);

		//ハンドラ使わない!!
		//Handler hdl = new Handler();
		// 3000ms遅延させてsplashHandlerを実行します。
		//hdl.postDelayed(new splashHandler(),10000);
	}
	@Override
	public void onAnimationEnd(Animation animation) {
	    Toast.makeText(this, "AnimationEnd", Toast.LENGTH_SHORT).show();
		// スプラッシュ完了後に実行するActivityを指定します。
		Intent intent = new Intent(getApplication(), TitleActivity.class);
		startActivity(intent);
		// SplashActivityを終了させます。
		SplashActivity.this.finish();
	}
	 
	@Override
	public void onAnimationRepeat(Animation animation) {
	    Toast.makeText(this, "AnimationRepeat", Toast.LENGTH_SHORT).show();
	}
	 
	@Override
	public void onAnimationStart(Animation animation) {
	    Toast.makeText(this, "AnimationStart", Toast.LENGTH_SHORT).show();
	}
}
