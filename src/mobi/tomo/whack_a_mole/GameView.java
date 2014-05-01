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
    //モグラの表示の有無 0=false 1=true
    int[] mole_visible = new int[9] ;
    //モグラのヒットの有無 0=false 1=true
    int[] mole_hit = new int[9] ;
    //モグラのヒットの表示時間
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
				mole_visible[iiii] = rnd.nextInt(2);
				//Log.d("mole_visible[iiii]---------", "" + mole_visible[iiii] + "");
				mole_status[iiii] = STANDBY;
				mole_hit[iiii] = 0;
				mole_visible_sec[iiii] = 3000; //すべてのモグラに3000ミリ秒
				if(mole_visible[iiii] == 1){
					mole_status[iiii] = VISIBLE;
				}else{
					mole_status[iiii] = HIDDEN;
				}
				   iiii++;
		   	}
		}
		//ゲームスタート
		STAGE = GAME;
    }
    
    private Activity mActivity;
    
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
			waitTime = 300;
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
			}else{
			}
			
		switch (STAGE) {
			case INIT:
				init();
				break;
			case GAME:
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
									mole_visible_sec[iii] = 3000;
									mole_status[iii] = HIDDEN;
									mole_visible[iii] = 1;
								}else if(mole_visible_sec[iii] == 3000){
									mole_visible_sec[iii] = mole_visible_sec[iii] - 300;
								}else{
									mole_visible_sec[iii] = mole_visible_sec[iii] - 300;
								}
								break;
						case HIDDEN:
								mole_hit[iii] = 0;
								if(mole_visible_sec[iii] < 0){
									mole_visible_sec[iii] = 3000;
									mole_status[iii] = VISIBLE;
									mole_visible[iii] = 0;
								}else if(mole_visible_sec[iii] == 3000){
									mole_visible_sec[iii] = mole_visible_sec[iii] - 300;
								}else{
									mole_visible_sec[iii] = mole_visible_sec[iii] - 300;
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
				break;
			case 3:
				//キャンバス用意ロックする
				c = holder.lockCanvas();
				//キャンバスを白く塗りつぶしてタッチした場所へ描画
				c.drawColor(Color.WHITE);
			    //c.drawColor(0,PorterDuff.Mode.CLEAR ); 

				c.drawText("[case:3]リザルトタップしてセッティング画面", viewWidth/2, viewHeight/2, paint);
				//キャンバスロックをはずす
			    holder.unlockCanvasAndPost(c);			
				break;
			case 4:
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
								int num = touchX + touchY*3;
								// 0=false 1=true;
								Log.i("num-------", "[" + num + "]");
							    if(mole_visible[num] == 1){
									Log.i("num-------hit", "[" + num + "]");
										if(mole_hit[num] == 0 && mole_status[num] == VISIBLE){
											GameCount++;
											mole_visible[num] = 0;
											mole_status[num] = HIDDEN;
											mole_visible_sec[num] = 3000;
										}
									//Log.i("GameCount", "" + GameCount + "");
										mole_hit[num] = 1; // ヒットした証拠
							   }
							}
						}
												
						if(GameCount > 10){
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
		            Log.i("tag", "ACTION_MOVE");
		            break;
		        case MotionEvent.ACTION_UP:
		            Log.i("tag", "ACTION_UP");
		            break;
		        case MotionEvent.ACTION_CANCEL:
		            Log.i("tag", "ACTION_CANCEL");
		        	break;
		        default:
		            Log.i("tag", "default");
		            break;
	        }

		 return true;

	}
	
}

