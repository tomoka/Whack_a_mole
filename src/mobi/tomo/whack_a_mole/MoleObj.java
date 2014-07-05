package mobi.tomo.whack_a_mole;
/*モグラ変数格納用Obj
 * 開始時刻を持たせる。
 * main.roopから開始時刻を常に監視させる
 * スレッドはmain.roopのみ
 * t%π
 * */
import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class MoleObj {

    //モグラの初期表示の有無 0=false 1=true
    int mole_start_visible = 0;
    //モグラの番地
    int mole_num_X = 0;
    int mole_num_Y = 0;
    //モグラの番号
    int mole_num = 0;
    //モグラのヒットの有無 0=false 1=true
    int mole_hit = 0;
	//モグラの表示時間のカウント
    int mole_visible_count = 0;
    //モグラの表示スタート時間
    int mole_visible_startTime = 0;
    //モグラの表示切り替え一定時間のmaxランダムをかけてそのときの表示時間を調節
    int mole_max_count = 4000;
    //モグラのヒットのステータス
    int mole_status = 0;
    
	// 描画状態の定義:ステータスと配列の番号をひも付けできる
	enum MoleAnimeStep {stay, step01, step02, finish};
	MoleAnimeStep moleAnimeStep;
	Variable variable = new Variable();
		
	//モグラアニメーションのタイム変数
	long old_time = 0;
	long passed_time = 0;
	
	//モグラアニメーション変数
	int moleAlpha;
	float moleScale = 1;
	float moleRotate = 1;
    
    /**モグラステップ用int*/
    static final int STANDBY = 5; //スタンバイ
    static final int VISIBLE = 6; //みえてる
    static final int HIDDEN = 7; //みえてない
    static final int HIT = 8; //ヒットした
    static final int FINISH = 9; //ヒット後
    
    int MOLE_STEP = STANDBY; 
    
	Random rnd = new Random();

	public void moleInit(int i,int ii){
		mole_start_visible = rnd.nextInt(2);
		mole_status = STANDBY;
		moleAnimeStep = MoleAnimeStep.stay;
		moleAlpha = 255;
		moleScale = 1;
		moleRotate = 0;
	    
	    /* mole_num
	     * [i=0 ii=0 0][i=0 ii=1 0][i=0 ii=2 0]
	     * [i=1 ii=0 3][i=1 ii=1 4][i=1 ii=2 5]
	     * [i=2 ii=0 6][i=2 ii=1 7][i=2 ii=2 8]
	     */
	    mole_num = i*3+ii;
	    mole_num_X = ii;
	    mole_num_Y = i;
		mole_max_count = rnd.nextInt(4000)+500;
	    
	    if (mole_start_visible == 1) {
	    	moleAnimeStep = MoleAnimeStep.step01;
	    }
	    old_time = System.currentTimeMillis();
	}
	public void moleObj() {
		switch (moleAnimeStep) {
		//stay　時間を費やす
		case stay:
				//何もしない待機
				moleAlpha = 255;
				moleScale = 1;
				moleRotate = 0;
				mole_start_visible = 0;
				mole_status = HIDDEN;
				mole_hit = 0;
				//passed_time = System.currentTimeMillis() - old_time;
				if(mole_max_count < passed_time){
					moleAnimeStep = MoleAnimeStep.step01;
					passed_time = 0;
					old_time = System.currentTimeMillis();
					mole_max_count = rnd.nextInt(4000)+500;
				}
			break;
		//Step01 見えてる
		case step01:
				mole_start_visible = 1;
				mole_status = VISIBLE;
				if(mole_hit == 1){
					passed_time = 0;
					old_time = System.currentTimeMillis();
					moleAnimeStep = MoleAnimeStep.step02;
					mole_max_count = rnd.nextInt(4000)+500;
				}
				if(mole_max_count < passed_time){
					passed_time = 0;
					old_time = System.currentTimeMillis();
					moleAnimeStep = MoleAnimeStep.finish;
					mole_max_count = rnd.nextInt(4000)+500;
				}
			break;
		//Step02　ヒットしたのできえる。。。
		case step02:
				Log.i("GameCount", "---step02");

				mole_start_visible = 1;
				mole_status = VISIBLE;
				mole_hit = 1;

				//きえたらさいごへ。。。。
				if(passed_time < 900){
					moleAlpha = (int) (moleAlpha - (moleAlpha * 0.1));
					moleScale = (float) TweenAnimation.easeOutCirc(passed_time,1,1.1,100);
					moleRotate = (float) (moleRotate + (moleRotate * 0.1));
				}else{
					mole_status = HIDDEN;
					mole_start_visible = 0;
					moleScale = 1;
					passed_time = 0;
					old_time = System.currentTimeMillis();
					mole_hit = 0;
					moleAnimeStep = MoleAnimeStep.finish;
					mole_max_count = rnd.nextInt(4000)+500;
				}
			break;
		//finish 最後
		case finish:
				//元に戻す
				mole_max_count = rnd.nextInt(4000)+500;
				moleAnimeStep = MoleAnimeStep.stay;
			break;
		default:
			break;
		}	
		moleUpdate();
	}
	public void moleUpdate() {
			//時間の更新のみ
			passed_time = System.currentTimeMillis() - old_time;
	}
	public MoleAnimeStep getStatus() {
		return moleAnimeStep;
	}
}
