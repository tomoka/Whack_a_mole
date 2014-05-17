package mobi.tomo.whack_a_mole;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class DrawMole {
	
    /**モグラステップ用int*/
    static final int STANDBY = 5; //スタンバイ
    static final int VISIBLE = 6; //みえてる
    static final int HIDDEN = 7; //みえてない
    static final int HIT = 8; //ヒットした
    static final int FINISH = 9; //ヒット後
	Random rnd = new Random();
   
	public void Draw(
			Canvas c,
			int[] mole_status,
			int[] mole_visible_sec,
			int[] mole_hit,
			int mole_sec,
			int imageWidth,
			int imageHeight,
			Bitmap grass,
			int viewWidth,
			int viewHeight,
			Paint paint,
			int mole_sec_fps,
			int mole_sec1,
			int GameCount
			) {
		int imageWidth1= imageWidth;
		int imageHeight1 = imageHeight;
		int viewWidth1= viewWidth;
		int viewHeight1 = viewHeight;
		
		if(c != null){
			//キャンバスを白く塗りつぶしてタッチした場所へ描画
			c.drawColor(Color.WHITE);
			int iii = 0;
			//９ならべる
			for(int i = 0;i< 3;i++){
			   for(int ii = 0;ii< 3;ii++){
				//c.drawBitmap(grass, drawX-imageWidth*i, drawY-imageHeight*ii, paint);
					//モグラのステータス
					switch (mole_status[iii]) {
						case VISIBLE:
							c.drawBitmap(grass, 100+imageWidth1*i, 100+imageHeight1*ii, paint);
								if(mole_visible_sec[iii] < 0){
									mole_visible_sec[iii] = rnd.nextInt(mole_sec1)+100;
									mole_status[iii] = HIDDEN;
									mole_hit[iii] = 0;
								}else{
									mole_visible_sec[iii] = mole_visible_sec[iii] - mole_sec_fps;
								}
								break;
						case HIDDEN:
								if(mole_visible_sec[iii] < 0){
									mole_visible_sec[iii] = rnd.nextInt(mole_sec1)+100;
									mole_hit[iii] = 0;
									mole_status[iii] = VISIBLE;
								}else{
									mole_visible_sec[iii] = mole_visible_sec[iii] - mole_sec_fps;
								}
								break;
						case HIT:
								mole_status[iii] = FINISH;
								break;
						case FINISH:
								mole_hit[iii] = 0; // ヒットした証拠
								mole_status[iii] = VISIBLE;
								break;
	
						default:
								break;
					}
				   iii++;
			   	}
			}
			//c.drawBitmap(goburin, drawX, drawY, paint);
			//Log.d("tag", "X:" + drawX + ",Y:" + drawY);
			c.drawText("タッチ回数は『"+GameCount+"』回です", viewWidth1/2, viewHeight1/2, paint);
			}		
	}
}

