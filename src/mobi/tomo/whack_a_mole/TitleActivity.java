package mobi.tomo.whack_a_mole;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
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

		    		// 完了後に実行するActivityを指定します。
		    		Intent intent = new Intent(getApplication(), FullscreenActivity.class);
		    		startActivity(intent);
		    		// TitleActivityを終了させます。
		    		TitleActivity.this.finish();

					
		            break;
		        default:
		            break;
	        }

		 return true;

	}

}
