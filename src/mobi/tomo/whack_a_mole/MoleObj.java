package mobi.tomo.whack_a_mole;
/*モグラ変数格納用Obj*/
import java.util.Random;

public class MoleObj {

    //モグラの初期表示の有無 0=false 1=true
    int mole_start_visible = 0;
    //モグラのヒットの有無 0=false 1=true
    int mole_hit = 0;
	//モグラの表示時間のカウント
    int mole_visible_count = 0;
    //モグラの表示切り替え一定時間のmaxランダムをかけてそのときの表示時間を調節
    static final int mole_max_count = 4000 ;
    //モグラのヒットのステータス
    int mole_status = 0;
	// 描画状態の定義:ステータスと配列の番号をひも付けできる
	enum MoleAnimeStep {stay, step01, step02, finish};
	MoleAnimeStep moleAnimeStep;
	
	//モグラアニメーションのタイム変数
	long old_time = 0;
	long passed_time = 0;
	
	//モグラアニメーション変数
	int moleAlpha;
	float moleScale;
	float moleRotate;
    
    /**モグラステップ用int*/
    static final int STANDBY = 5; //スタンバイ
    static final int VISIBLE = 6; //みえてる
    static final int HIDDEN = 7; //みえてない
    static final int HIT = 8; //ヒットした
    static final int FINISH = 9; //ヒット後
    
    int MOLE_STEP = STANDBY; 
    
	Random rnd = new Random();

	public void MoleInit(){
		mole_start_visible = rnd.nextInt(2);
		mole_status = STANDBY;
		moleAnimeStep = MoleAnimeStep.stay;
		moleAlpha = 0;
		moleScale = 1;
		moleRotate = 0;
	}
	
	public void stay(){
		moleAlpha = 0;
		moleScale = 1;
		moleRotate = 0;
	}
	public void step01(){
		mole_start_visible = 1;
		mole_status = VISIBLE;
		moleAnimeStep = MoleAnimeStep.step01;
	}
	public void step02(){
		mole_start_visible = 1;
		mole_status = VISIBLE;
		moleAnimeStep = MoleAnimeStep.step01;
		mole_hit = 1;
		//きえたらさいごへ。。。。
		if(moleAlpha > 0){
			moleAlpha = (int) (moleAlpha - (moleAlpha * 0.4));
			moleScale = (float) (moleScale + (moleScale * 0.1));
			moleRotate = (float) (moleRotate + (moleRotate * 0.1));
		}
	}
	public void finish(){
		stay();
	}
	public void run() {
		moleUpdate();
		//stay　時間を費やす
		if (moleAnimeStep.equals(MoleAnimeStep.stay)) {
			//何もしない待機
			mole_start_visible = 0;
			mole_status = HIDDEN;
			moleAnimeStep = MoleAnimeStep.stay;
			mole_hit = 0;
		}
		//Step01 見えてる
		if (moleAnimeStep.equals(MoleAnimeStep.step01)) {
			if(mole_hit == 1){
				step02();
			}
		}
		//Step02　ヒットしたのできえる。。。
		if (moleAnimeStep.equals(MoleAnimeStep.step02)) {
			if(moleAlpha <= 0){
				finish();
			}else{
				//消えるまで残留
				step02();
			}
		}
	}
	
	public void moleUpdate() {
		if (moleAnimeStep.equals(moleAnimeStep.stay)) {
			old_time = System.currentTimeMillis();
			passed_time = System.currentTimeMillis() - old_time;
			if(mole_max_count < passed_time){
				step01();
			}
		}
		else if (moleAnimeStep.equals(moleAnimeStep.step01)||moleAnimeStep.equals(moleAnimeStep.step02)) {
			//時間の更新
			passed_time = System.currentTimeMillis() - old_time;
			if(mole_max_count < passed_time && moleAnimeStep.equals(moleAnimeStep.step01)){
				finish();
			}
		}
		else if (moleAnimeStep.equals(moleAnimeStep.finish)) {
			passed_time = 0;
			stay();
		}
	}
	
	public MoleAnimeStep getStatus() {
		return moleAnimeStep;
	}
}
