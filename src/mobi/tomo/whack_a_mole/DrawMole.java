package mobi.tomo.whack_a_mole;

import android.graphics.Color;
import android.util.Log;

public class DrawMole {
	//キャンバス用意ロックする
	c = holder.lockCanvas();
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
					c.drawBitmap(grass, 100+imageWidth*i, 100+imageHeight*ii, paint);
					if(mole_visible_sec[iii] < 0){
						mole_visible_sec[iii] = rnd.nextInt(mole_sec)+100;
						mole_status[iii] = HIDDEN;
						mole_hit[iii] = 0;
					}else{
						mole_visible_sec[iii] = mole_visible_sec[iii] - mole_sec_fps;
					}
					break;
			case HIDDEN:
					if(mole_visible_sec[iii] < 0){
						mole_visible_sec[iii] = rnd.nextInt(mole_sec)+100;
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
	Log.d("tag", "X:" + drawX + ",Y:" + drawY);
	c.drawText("タッチ回数は『"+GameCount+"』回です", viewWidth/2, viewHeight/2, paint);
	}		
	//キャンバスロックをはずす
    holder.unlockCanvasAndPost(c);

}
