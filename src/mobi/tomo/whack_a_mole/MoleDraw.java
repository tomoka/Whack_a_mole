package mobi.tomo.whack_a_mole;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class MoleDraw {
	
    /**モグラステップ用int*/
    static final int STANDBY = 5; //スタンバイ
    static final int VISIBLE = 6; //みえてる
    static final int HIDDEN = 7; //みえてない
    static final int HIT = 8; //ヒットした
    static final int FINISH = 9; //ヒット後

    //モグラの表示切り替え一定時間のmaxランダムをかけてそのときの表示時間を調節
    static final int mole_max_count = 4000 ;

    Random rnd = new Random();
	
    //モグラオブジェクト準備
	MoleObj moleObj[];
   
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
			int viewScale,
			Paint paint,
			int mole_sec_fps,
			int mole_sec1,
			int GameCount
			) {
		int imageWidth1= imageWidth;
		int imageHeight1 = imageHeight;
		int viewWidth1= viewWidth;
		int viewHeight1 = viewHeight;

		//もぐらオブジェクトを９つ生成
		for(int m=0;m<9;m++){
			moleObj[m] = new MoleObj();
		}

		if(c != null){
			//キャンバスを白く塗りつぶしてタッチした場所へ描画
			c.drawColor(Color.WHITE);
			int m = 0;
			//９ならべる
			for(int i = 0;i< 3;i++){
			   for(int ii = 0;ii< 3;ii++){
				//c.drawBitmap(grass, drawX-imageWidth*i, drawY-imageHeight*ii, paint);
					//モグラのステータス
					switch (moleObj[m].mole_status) {
						case VISIBLE:
							c.drawBitmap(grass, 100+imageWidth1*i*viewScale, 100+imageHeight1*ii*viewScale, paint);
								if(moleObj[m].mole_visible_count < 0){
									moleObj[m].mole_visible_count = rnd.nextInt(mole_max_count)+100;
									moleObj[m].mole_status = HIDDEN;
									moleObj[m].mole_hit = 0;
								}else{
									moleObj[m].mole_visible_count = moleObj[m].mole_visible_count - mole_sec_fps;
								}
								break;
						case HIDDEN:
								if(moleObj[m].mole_visible_count < 0){
									moleObj[m].mole_visible_count = rnd.nextInt(mole_max_count)+100;
									moleObj[m].mole_hit = 0;
									moleObj[m].mole_status = VISIBLE;
								}else{
									moleObj[m].mole_visible_count = moleObj[m].mole_visible_count - mole_sec_fps;
								}
								break;
						case HIT:
								moleObj[m].mole_status = FINISH;
								break;
						case FINISH:
								moleObj[m].mole_hit = 0; // ヒットした証拠
								moleObj[m].mole_status = VISIBLE;
								break;
	
						default:
								break;
					}
				   m++;
			   	}
			}
			//c.drawBitmap(goburin, drawX, drawY, paint);
			//Log.d("tag", "X:" + drawX + ",Y:" + drawY);
			c.drawText("タッチ回数は『"+GameCount+"』回です", viewWidth1/2, viewHeight1/2, paint);
			}		
	}

}

