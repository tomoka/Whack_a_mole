package mobi.tomo.whack_a_mole;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TitleActivity extends Activity {
	
    int windowHeight;
    int windowWidth;
	String TAG;
	
	TitleView titleView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		// タイトルを非表示にします。
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//画面を縦方向で固定する
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //タイトルバーの非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		/*
	     * 端末の大きさを取得
	     */
		// ウィンドウマネージャのインスタンス取得
		WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
		// ディスプレイのインスタンス生成
		Display disp = wm.getDefaultDisplay();
		
		windowWidth = disp.getWidth();
		windowHeight = disp.getHeight();

		LinearLayout titlePage = new LinearLayout(this);
        setContentView(titlePage);
		try {
	        titleView = new TitleView(this, windowWidth,windowHeight);
	        titlePage.addView(titleView,windowWidth,windowHeight);
			Toast.makeText(this,"タイトルclassの読み込みに成功しました。", Toast.LENGTH_LONG).show();
		} catch (Exception ex) {
			Toast.makeText(this,"タイトルclassの読み込みに失敗しました。", Toast.LENGTH_LONG).show();
		}
       
		}
	
	@Override
	protected void onResume(){
		super.onResume();
		Log.i(TAG, "onResume");
		};
	
	@Override
		protected void onStart(){
			super.onStart();
			Log.i(TAG, "onStart");
			}
		
	@Override
		protected void onRestart(){
			super.onRestart();
			Log.i(TAG, "onRestart");
			// 再開処理
			}
		
	@Override
		protected void onPause(){
			super.onPause();
			Log.i(TAG, "onPause");
			// 終了処理
			}
		
	@Override
		protected void onStop(){
			super.onStop();
			Log.i(TAG, "onStop");
			//スレッドを破棄する
			}
		
	@Override
		protected void onDestroy(){
			super.onDestroy();
			Log.i(TAG, "onDestroy");
			//スレッドを破棄する
			titleView.thread = null;
			// スプラッシュ完了後に実行するActivityを指定します。
			//Intent intent = new Intent(getApplication(), FullscreenActivity.class);
			//startActivity(intent);
			// SplashActivityを終了させます。
			//TitleActivity.this.finish();
			}
}

