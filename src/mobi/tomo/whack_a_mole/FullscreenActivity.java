package mobi.tomo.whack_a_mole;

/*
 * やる事タッチイベントの内容をクラスに切り出す
 * ここに描いたスイッチ文は削除
 * */

import mobi.tomo.whack_a_mole.util.SystemUiHider;
import mobi.tomo.whack_a_mole.GameView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {

		/** ログ用タグ */
		private static String TAG = "gameView";
		int windowWidth = 0;
		int windowHeight = 0;
		//GameView gameView = new GameView(null, windowWidth, windowHeight);				
       
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			Log.i("tag", "onCreate");

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

			LinearLayout l = new LinearLayout(this);
	        setContentView(l);
	        
				try {
			        //Intent intent = getIntent();
					//windowWidth = intent.getIntExtra("windowWidth",windowWidth);
					//windowHeight = intent.getIntExtra("windowHeight",windowHeight);
					Log.i("tag", ""+ windowWidth + "");
					Log.i("tag", ""+ windowHeight + "");
	
			        GameView gameView = new GameView(this, windowWidth, windowHeight);
			        l.addView(gameView,windowWidth,windowHeight);
					Toast.makeText(this,"ゲームclassの読み込みに成功しました。", Toast.LENGTH_LONG).show();
				} catch (Exception ex) {
					Toast.makeText(this,"ゲームclassの読み込みに失敗しました。", Toast.LENGTH_LONG).show();
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
			
    		// スプラッシュ完了後に実行するActivityを指定します。
    		Intent intent = new Intent(getApplication(), TitleActivity.class);
    		startActivity(intent);
    		// SplashActivityを終了させます。
    		FullscreenActivity.this.finish();
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
			}
	
	//タッチ入力処理
	@Override
		public boolean onTouchEvent(MotionEvent event) {
		 switch (event.getAction()) {
		        case MotionEvent.ACTION_DOWN:
		            Log.i("tag", "ACTION_DOWN");
		            
		            break;
		        case MotionEvent.ACTION_MOVE:
		        case MotionEvent.ACTION_UP:
		        case MotionEvent.ACTION_CANCEL:
		        default:
		            break;
	        }

		 return true;

	}

}
