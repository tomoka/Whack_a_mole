package mobi.tomo.whack_a_mole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class TitleActivity extends Activity {
	
	//private int windowWidth;
	//private int windowHeight;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// タイトルを非表示にします。
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// splash.xmlをViewに指定します。
		setContentView(R.layout.title);
		
		
	}

	//タッチ入力処理
	@Override
		public boolean onTouchEvent(MotionEvent event) {
		 switch (event.getAction()) {
		        case MotionEvent.ACTION_DOWN:
		            Log.i("tag", "ACTION_DOWN");
					// スプラッシュ完了後に実行するActivityを指定します。
		            //windowWidth,windowHeight
					//Intent intent = new Intent(getApplicationContext(),FullscreenActivity.class);
		    		// スプラッシュ完了後に実行するActivityを指定します。
		    		Intent intent = new Intent(getApplication(), FullscreenActivity.class);
		    		startActivity(intent);
		    		// SplashActivityを終了させます。
		    		TitleActivity.this.finish();

					//intent.putExtra("windowWidth", windowWidth);
					//intent.putExtra("windowHeight", windowHeight);
					
					/*try {
						startActivity(intent);
						Toast.makeText(this,"FullscreenActivityへの画面遷移に成功しました。", Toast.LENGTH_LONG).show();
					} catch (Exception ex) {
						Toast.makeText(this,"FullscreenActivityへの画面遷移に失敗しました。", Toast.LENGTH_LONG).show();
					}*/

					// TitleActivityを終了させます。
					//TitleActivity.this.finish();
					
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
