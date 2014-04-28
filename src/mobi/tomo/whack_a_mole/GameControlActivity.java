package mobi.tomo.whack_a_mole;

import android.app.Activity;
import android.content.Intent;
import android.view.Window;

public class GameControlActivity extends Activity {
	
	public void finish_gameActivity(){
		// タイトルを非表示にします。
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// splash.xmlをViewに指定します。
		setContentView(R.layout.title);
		// 完了後に実行するActivityを指定します。
		Intent intent = new Intent(getApplication(), TitleActivity.class);
		startActivity(intent);
		// Activityを終了させます。
		this.finish();
	}
}
