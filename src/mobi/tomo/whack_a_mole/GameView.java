package mobi.tomo.whack_a_mole;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import java.lang.Runnable;
import java.util.Random;

//コールバックとrun()を使えるようにクラス(extends)に更に道具(implements)をセット。
public class GameView extends SurfaceView implements Callback, Runnable {
	static final long FPS = 20;
	static final long FRAME_TIME = 1000 / FPS;

	//SurfaceHolderをholderに名前付け
    SurfaceHolder holder;
    Thread thread = new Thread(this);

    Handler mHandler = new Handler();
	
    //画像読み込み
    Resources res = this.getContext().getResources();
    Bitmap grass = BitmapFactory.decodeResource(res, R.drawable.grass);
    Bitmap goburin = BitmapFactory.decodeResource(res, R.drawable.goburin);
    Canvas c = null;
    
    /**ステージ用int*/
    static final int INIT = 1;
    static final int GAME = 2;
    static final int RESULT = 3;
    static final int SETTING = 4;
    
    int STAGE = INIT;
    
    /**ゲームカウント*/
    int GameCount = 0;

    /**モグラ用のカウント*/
    int[] mole = new int[9] ;
    //モグラの初期表示の有無 0=false 1=true
    int[] mole_start_visible = new int[9] ;
    //モグラのヒットの有無 0=false 1=true
    int[] mole_hit = new int[9] ;
    //モグラのヒットの表示切り替え一定時間
    static final int mole_sec = 4000 ;
	//フレームレート 1000/14
    static final int mole_sec_fps = 14 ;
    int[] mole_visible_sec = new int[9] ;
    //モグラのヒットのステータス
    int[] mole_status = new int[9] ;
    
    /**モグラステップ用int*/
    static final int STANDBY = 5; //スタンバイ
    static final int VISIBLE = 6; //みえてる
    static final int HIDDEN = 7; //みえてない
    static final int HIT = 8; //ヒットした
    static final int FINISH = 9; //ヒット後
    
    int MOLE_STEP = STANDBY;    
    
	Random rnd = new Random();
    
    /** ログ用タグ */
    String TAG = "gameView";
    /** 待ち時間 */
    int WAIT_TIME = 30;
    /** 描画範囲 */
    int DRAW_RANGE = 50;
    /** 背景色 */
    int BACKGROUND_COLOR = Color.GREEN;
 
    /** 描画用ペイント */
    Paint paint;
    /** 描画座標：X(初期値は表示されない領域)  */
    float drawX = 0;
    /** 描画座標：Y(初期値は表示されない領域) */
    float drawY = 0;
    /** イメージの大きさ：横 */
    int imageWidth = grass.getWidth();
    /** イメージの大きさ：高さ */
    int imageHeight = grass.getHeight();
    /**もぐらの配列*/
    
    /** ループ内実行判定 */
    boolean execFlg = true;
	long loopCount;

    /** メインループ用スレッド 
     * @param windowHeight 
     * @param windowWidth */
    //Thread mainLoop;
    int viewHeight;
    int viewWidth;
    
    public void init(){
		//モグラの初期化
		int iiii = 0;
		//９ならべる
		for(int i = 0;i< 3;i++){
		   for(int ii = 0;ii< 3;ii++){
			   //c.drawBitmap(grass, drawX-imageWidth*i, drawY-imageHeight*ii, paint);
				//見える見えない初期値　1=true(みえる) 0=false(みえない)
				mole_start_visible[iiii] = rnd.nextInt(2);
				mole_status[iiii] = STANDBY;
				mole_hit[iiii] = 0;
				if(mole_start_visible[iiii] == 1){
					mole_status[iiii] = VISIBLE;
					mole_visible_sec[iiii] = rnd.nextInt(mole_sec)+100; //すべてのモグラにランダム+100ミリ秒
				}else{
					mole_status[iiii] = HIDDEN;
					mole_visible_sec[iiii] = rnd.nextInt(mole_sec)+100; //すべてのモグラにランダム+100ミリ秒
				}
					Log.d("mole_visible[iiiii]---------", "" + mole_visible_sec[iiii] + "");
					iiii++;
		   	}
		}
		//ゲームスタート
		STAGE = GAME;
    }
    
    public void drawMole(){
    }
    
    private Activity mActivity;
    DrawMole drawMole = new DrawMole;
    
	/* コンストラクタ 引数1  @param context*/
	public GameView(Context context, int windowWidth, int windowHeight) {
        super(context);
        mActivity = (Activity) context;
        
        paint = new Paint();
		paint.setARGB(200,0,0,0);
		paint.setTextSize (63);
		paint.setTextAlign(Paint.Align.CENTER);

		viewWidth = windowWidth;
		viewHeight = windowHeight;
        holder = getHolder();
        //callbackメソッド（下の３つ）を登録
        //callbackメソッドを登録
        holder.addCallback(this);
		Log.i("tag", "GameView");
		}    

    //サーフェイス生成で実行される
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
		Log.i("tag", "surfaceCreated");
		//スレッドで描画開始
        thread.start();
   }

    //サーフェイス変化で実行される
    @Override
    public void surfaceChanged(SurfaceHolder holder, int f, int w, int h) {
		Log.i("tag", "surfaceChanged");
    }

    //端末の戻るボタンでサーフェイス破棄が実行される
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("tag", "surfaceDestroyed");
		//スレッドを破棄する
		thread = null;
   }
    
    //描画処理
    @Override
    public void run() {
       Log.i("tag", "run-loop");
       
		loopCount = 0;
		long waitTime = 0;
		long startTime = System.currentTimeMillis();

		while(thread != null){
			loopCount++;
			Log.i("tag", "" +loopCount+"");
			//waitTime = (loopCount * FRAME_TIME) - (System.currentTimeMillis() - startTime);

			waitTime = mole_sec_fps;
            Log.i("tag", "" +waitTime+"");
			if( waitTime > 0 ){
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Log.i("tag", "loopCount:" +loopCount+"");
				loopCount = 0;
			}
		
		//ステージと時間の管理
		switch (STAGE) {
			case INIT:
				//モグラ初期化メゾット
				init();
				break;
			case GAME:
				//モグラ描画メゾット
				drawMole();
				break;
			case RESULT:
				//キャンバス用意ロックする
				c = holder.lockCanvas();
				//キャンバスを白く塗りつぶしてタッチした場所へ描画
				c.drawColor(Color.WHITE);
			    //c.drawColor(0,PorterDuff.Mode.CLEAR ); 

				c.drawText("[case:3]リザルトタップしてセッティング画面", viewWidth/2, viewHeight/2, paint);
				//キャンバスロックをはずす
			    holder.unlockCanvasAndPost(c);			
				break;
			case SETTING:
				//キャンバス用意ロックする
				c = holder.lockCanvas();
				//キャンバスを白く塗りつぶしてタッチした場所へ描画
				c.drawColor(Color.WHITE);
			    //c.drawColor(0,PorterDuff.Mode.CLEAR ); 

				c.drawText("[case:4]セッティング画面タップしてスタート", viewWidth/2, viewHeight/2, paint);
				//キャンバスロックをはずす
			    holder.unlockCanvasAndPost(c);			
				break;
		}
        Log.i("tag", "STAGE:" +STAGE+"");
    }
	}
	//タッチ入力処理
	@Override
		public boolean onTouchEvent(MotionEvent event) {
		 switch (event.getAction()) {
		        case MotionEvent.ACTION_DOWN:
		            Log.i("tag", "ACTION_DOWN");
			   		Log.d("tag", "X:" + event.getX() + ",Y:" + event.getY());
					drawX = event.getX();
					drawY = event.getY();
			        switch (STAGE){
						case INIT:
							break;
						case GAME:
							//タッチ領域判定
							//小数点は切り捨て
							int touchX = (int) ((drawX-100)/imageWidth);
							int touchY = (int) ((drawY-100)/imageHeight);
							
							Log.i("GameCount", "-touch-[" + touchX +","+ touchY + "]");
	
							if(touchX < 4 && touchX >= 0){
								if(touchY < 4 && touchY >= 0){
								   //モグラがtrueの場合かうんと
									//touchYに３をかけると２段目は３から始まる。３段目は６から始まる。
									int num = touchX*3 + touchY;
									Log.i("num-------", "[" + num + "]");
								    if(mole_status[num] == VISIBLE){
										Log.i("num-------hit", "[" + num + "]");
											if(mole_hit[num] == 0){
												GameCount++;
												mole_status[num] = HIDDEN;
												mole_visible_sec[num] = mole_sec; //ステータスの切り替わりで 3000ミリ秒追加
												mole_hit[num] = 1; // ヒットした証拠
											}
											Log.i("num-------GameCount", "[" + GameCount + "]");
								   }
								}
							}
													
							if(GameCount > 30){
								STAGE = RESULT;
							 }
							break;
						case RESULT:
							STAGE = SETTING;
							//スレッド破棄
							thread = null;
							break;
						case SETTING:
							 //STAGE = TITLE;
							GameCount = 0;
							//surfaceDestroyed(null);
							mActivity.finish();
							break;
							}

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

