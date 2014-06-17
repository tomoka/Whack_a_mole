package mobi.tomo.whack_a_mole;

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
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class TitleView extends SurfaceView implements Callback, Runnable {

	//SurfaceHolderをholderに名前付け
    SurfaceHolder holder;
    Thread thread = new Thread(this);
    Handler mHandler = new Handler();

    private Activity mActivity;
    Matrix matrix = new Matrix();
    
    Paint paint = new Paint();

    //Thread mainLoop;
    int viewHeight;
    int viewWidth;

    //画像読み込み
    Resources res = this.getContext().getResources();
    Bitmap start_btn = BitmapFactory.decodeResource(res, R.drawable.start_btn);
    Bitmap help_btn = BitmapFactory.decodeResource(res, R.drawable.help_btn);
    Bitmap big_mole = BitmapFactory.decodeResource(res, R.drawable.moleobj_b);
    Bitmap bg_start = BitmapFactory.decodeResource(res, R.drawable.bg_start);
    Canvas c = null;
    
    /** big_moleの大きさ */
    int big_moleWidth = big_mole.getWidth();
    int big_moleHeight = big_mole.getHeight();
    
    /** start_btnの大きさ */
    int start_btnWidth = start_btn.getWidth();
    int start_btnHeight = start_btn.getHeight();
    
    /** help_btnの大きさ */
    int help_btnWidth = help_btn.getWidth();
    int help_btnHeight = help_btn.getHeight();

    //画面サイズ用スケール
    float viewScale;
    
	boolean canvaseLock = false;

    public TitleView(Context context, int windowWidth, int windowHeight) {
		super(context);
		// TODO Auto-generated constructor stub

        mActivity = (Activity) context;
        
        //Thread mainLoop;
        viewHeight = windowHeight;
        viewWidth = windowWidth;
        
	    float min = Math.min(viewWidth, viewHeight);
	    viewScale = min/720;

		holder = getHolder();
        //callbackメソッド（下の３つ）を登録
        //callbackメソッドを登録
        holder.addCallback(this);
        Log.i("tag", "TitleView");

    }


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(thread != null){
			
			if(canvaseLock){
				c = holder.lockCanvas();
				//背景用のマトリックス
				matrix.setScale(
						(float) viewWidth/720,
						(float) viewHeight/1280,
						0,
						0
						);
				//matrix.setTranslate(0,0);
				c.drawBitmap(bg_start,matrix, paint);
				
				
				//タイトル用もぐらのマトリックス big_moleWidth
				matrix.setScale(
						(float) viewScale,
						(float) viewScale
						);
				matrix.postTranslate(
						(viewWidth-big_moleWidth*viewScale)/2,
						(float) ((viewHeight/2.5)-(big_moleHeight*viewScale/2))
						);
				c.drawBitmap(big_mole,matrix, paint);

				//タイトル用スタートボタンのマトリックス big_moleWidth
				matrix.setScale(
						(float) viewScale,
						(float) viewScale
						);
				matrix.postTranslate(
						((viewWidth - start_btnWidth*viewScale)/2),
						(800*viewScale)
						);
				c.drawBitmap(start_btn,matrix, paint);

				//タイトル用ヘルプボタンのマトリックス big_moleWidth
				matrix.setScale(
						(float) viewScale,
						(float) viewScale
						);
				matrix.postTranslate(
						((viewWidth - help_btnWidth*viewScale)/2),
						(950*viewScale)
						);
				c.drawBitmap(help_btn,matrix, paint);
				
			    holder.unlockCanvasAndPost(c);
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
        Log.i("tag", "surfaceCreated");
        canvaseLock = true;
        thread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		canvaseLock = false;
        thread = null;
		
	}

	//タッチ入力処理
	@Override
		public boolean onTouchEvent(MotionEvent event) {
		 switch (event.getAction()) {
		        case MotionEvent.ACTION_DOWN:
		            Log.i("tag", "ACTION_DOWN");

		    		canvaseLock = false;
		            thread = null;
		    		// 完了後に実行するActivityを指定します。
		    		Intent intent = new Intent(mActivity.getApplication(), FullscreenActivity.class);
		    		mActivity.startActivity(intent);
		    		//TitleActivityを終了させます。
		    		mActivity.finish();

		            break;
		        default:
		            break;
	        }

		 return true;

	}

}
