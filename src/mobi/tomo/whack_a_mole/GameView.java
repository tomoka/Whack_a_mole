package mobi.tomo.whack_a_mole;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    Matrix matrix = new Matrix();
    /** 描画座標：X(初期値は表示されない領域)  */
    float drawX = 0;
    /** 描画座標：Y(初期値は表示されない領域) */
    float drawY = 0;
    /** イメージの大きさ：横 */
    int imageWidth = grass.getWidth();
    /** イメージの大きさ：高さ */
    int imageHeight = grass.getHeight();
    
    float moleScale = 4;
    /**もぐらの配列*/
    
    /** ループ内実行判定 */
    boolean canvaseLock = false;
    boolean execFlg = true;

    /** メインループ用スレッド 
     * @param windowHeight 
     * @param windowWidth */
    //Thread mainLoop;
    int viewHeight;
    int viewWidth;

    //画面サイズ用スケール
    float viewScale = 1;
	//もぐらオブジェクトの生成準備
    //複数クラスを増やす時の文法
	MoleObj[] moleObj = new MoleObj[9];

    Variable variable = new Variable();

    private Activity mActivity;

    public void init(){
		//モグラの初期化
		//９ならべる
		for(int i = 0;i< 3;i++){
		   for(int ii = 0;ii< 3;ii++){
				moleObj[i*3+ii] = new MoleObj();
			   	moleObj[i*3+ii].moleInit(i,ii);
		   	}
		}
		//ゲームスタート
		STAGE = GAME;
    }
    
	/* コンストラクタ 引数1  @param context*/
	public GameView(Context context, int windowWidth, int windowHeight) {
        super(context);
        mActivity = (Activity) context;
        
        viewHeight = windowHeight;
        viewWidth = windowWidth;
        
	    float m = Math.min(viewWidth, viewHeight);
	    viewScale = m/1080;
		Log.i("viewScale", "viewScale>>>>>" + viewScale + "");

        paint = new Paint();
		paint.setARGB(200,0,0,0);
		paint.setTextSize(64*viewScale);
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
		canvaseLock = true;
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
		canvaseLock = false;
		//スレッドを破棄する
		thread = null;
   }
    
    //描画処理
    @Override
    public void run() {
    	Log.i("tag", "run-loop");
		long waitTime = 0;

		while(thread != null){

			waitTime = variable.mole_sec_fps;
			if( waitTime > 0 ){
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	        Log.i("tag", "STAGE:" +viewScale+"");
	        Log.i("scale", "scale:" +viewScale+"");

		//ステージと時間の管理
		switch (STAGE) {
			case INIT:
				init();
				break;
			case GAME:
			    try {
		        	//キャンバスがあったら、画像を描き込む
		    	    //if (c != null) {
						//キャンバス用意ロックする
						if(canvaseLock) c = holder.lockCanvas();
						//キャンバスを白く塗りつぶしてタッチした場所へ描画
						c.drawColor(Color.WHITE);
						
						//モグラ描画メゾット
						//９ならべる
						for(int i = 0;i< 3;i++){
						   for(int ii = 0;ii< 3;ii++){
							   	moleObj[i*3+ii].moleObj();

								switch (moleObj[i*3+ii].moleAnimeStep) {
									case stay:
										//何も書き込まない
										break;
									case step01:
										paint.setAlpha(255);
										matrix.setTranslate(140*viewScale+imageWidth*ii*viewScale, 140*viewScale+imageHeight*i*viewScale);
										matrix.preScale(viewScale, viewScale);
										c.drawBitmap(grass,matrix, paint);
										break;
									case step02:										
										//消えるアニメーション中
										/*paint.setAlpha(moleObj[i*3+ii].moleAlpha);*/
										float x = (float) ((140*viewScale)+((imageWidth*viewScale*ii*1.5)-(imageWidth*viewScale*moleObj[i*3+ii].moleScale)/2));
										float y = (float) ((140*viewScale)+((imageHeight*viewScale*i*1.5)-(imageHeight*viewScale*moleObj[i*3+ii].moleScale)/2));
										
										//Log.i("viewScale", "viewScale>>>>>" + (moleObj[i*3+ii].moleScale)+ " ");

										matrix.setTranslate(x, y);
										//matrix.preScale(moleObj[i*3+ii].moleScale*viewScale, moleObj[i*3+ii].moleScale*viewScale);
										c.drawBitmap(goburin,matrix,paint);
										break;
									case finish:
										//何も書き込まない
										break;
									default:
										break;
								}
						   	}
						}
						paint.setAlpha(255);
						c.drawText("[case:2]ゲーム中 スコア："+ variable.GameCount +"", viewWidth/2, viewHeight/2, paint);
						//キャンバスロックをはずす
					    holder.unlockCanvasAndPost(c);
						if (!canvaseLock) {
							//c.drawColor(Color.WHITE);
							return;
						}

		           // }
			    }catch(ArithmeticException e){
		            //なにもしない
			    	e.printStackTrace();
		        } finally {
		        	//書き込みを終了させる
		            //if (c != null) holder.unlockCanvasAndPost(c);
		        	//書き込みを終了ステータスアップデート
			      	//ステータス管理
		        }

				break;
			case RESULT:
				//キャンバス用意ロックする
				c = holder.lockCanvas();
				//キャンバスを白く塗りつぶしてタッチした場所へ描画
				c.drawColor(Color.WHITE);
				paint.setAlpha(255);
				c.drawText("[case:3]リザルトタップしてセッティング画面", viewWidth/2, viewHeight/2, paint);
				//キャンバスロックをはずす
			    holder.unlockCanvasAndPost(c);			
				break;
			case SETTING:
				//キャンバス用意ロックする
				c = holder.lockCanvas();
				//キャンバスを白く塗りつぶしてタッチした場所へ描画
				c.drawColor(Color.WHITE);
				paint.setAlpha(255);
				c.drawText("[case:4]セッティング画面タップしてスタート", viewWidth/2, viewHeight/2, paint);
				//キャンバスロックをはずす
			    holder.unlockCanvasAndPost(c);			
				break;
		}
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
							int touchX = (int) ((drawX-140*viewScale)/(imageWidth*viewScale));
							int touchY = (int) ((drawY-140*viewScale)/(imageHeight*viewScale));
							
							
					   		Log.d("touchtouch", "drawX-140*viewScale:" + (drawX-140*viewScale) + "");
					   		Log.d("touchtouch", "imageWidth*viewScale:" + (imageWidth*viewScale) + "");
					   		Log.d("touchtouch", "imageWidth:" + imageWidth + ",imageHeight:" + imageHeight);
					   		Log.d("touchtouch", "viewScale:" + viewScale);
					   		Log.d("touchtouch", "X:" + drawX + ",Y:" + drawY);
					   		Log.d("touchtouch", "X:" + touchX + ",Y:" + touchY);
					   	
							if(touchX < 4 && touchX >= 0){
								if(touchY < 4 && touchY >= 0){
								   //モグラがtrueの場合かうんと
									//touchYに３をかけると２段目は３から始まる。３段目は６から始まる。
									int num = (int) (touchX + touchY*3);
							   		Log.d("touchtouch", "num:" + num);
								    if(moleObj[num].moleAnimeStep == moleObj[num].moleAnimeStep.step01){
										moleObj[num].mole_hit = 1; // ヒットした証拠
										variable.GameCount++;
								   }else{
									   variable.GameCount--;
								   }
								}
							}
													
							if(variable.GameCount >= 20){
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
							variable.GameCount = 0;
							mActivity.finish();
							break;
							}

		            break;
		        default:
		            break;
	        }

		 return true;

	}
	
}

