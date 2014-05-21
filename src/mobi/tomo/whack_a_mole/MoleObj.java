package mobi.tomo.whack_a_mole;

import java.util.Random;

public class MoleObj {

    //モグラの初期表示の有無 0=false 1=true
    int mole_start_visible = 0;
    //モグラのヒットの有無 0=false 1=true
    int mole_hit = 0;
    //モグラのヒットの表示切り替え一定時間
    static final int mole_sec = 4000 ;
	//フレームレート 1000/14
    static final int mole_sec_fps = 14 ;
    int mole_visible_sec = 0;
    //モグラのヒットのステータス
    int mole_status = 0;
    
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
		}
	
	public void MoleStatus(){
		mole_start_visible = rnd.nextInt(2);
		mole_status = STANDBY;
		}
	
}
