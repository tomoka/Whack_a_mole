package mobi.tomo.whack_a_mole;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import aurelienribon.tweenengine.*;

	public class AnimeMole implements TweenAccessor<AnimeMole>
	{
		protected Paint paint;
		//----------------------------
		//プロパティ
		public float x;
		public float y;
		public float r;
		public float a;
		//----------------------------
		//Tween用定数
		  public static final int POSITION_X = 1;
		  public static final int POSITION_Y = 2;
		  public static final int POSITION_XY = 3;
		  public static final int ROTATION = 4;
		  public static final int SCALE_XY = 5;
		  public static final int ALPHA = 6;
	 
		public AnimeMole()
	    {
			paint = new Paint();
			paint.setColor(Color.WHITE);
			x=0;
			y=0;
			r =40;
	    }
	 
		/**
		 * 描画
		 * @param canvas
		 */
		public void drawAnimeMole(Canvas c)
		{
			c.drawCircle(x, y, r, paint);
		}
	 
		@Override
		public int getValues(AnimeMole mole, int tweenType, float[] returnValues) {
			// TODO Auto-generated method stub
			switch (tweenType) {
	            case POSITION_X: returnValues[0] = x; return 1;
	            case POSITION_Y: returnValues[0] = y; return 1;
	            case POSITION_XY:
	                    returnValues[0] = x;
	                    returnValues[1] = y;
	                    return 2;
	            case ROTATION :
            		returnValues[0] = r;
            		return 2;
	            case ALPHA :
            		returnValues[0] = r;
            		return 2;
	            default: assert false; return 0;
		    }
		}

		@Override
		public void setValues(AnimeMole mole, int tweenType, float[] newValues) {
			// TODO Auto-generated method stub
            switch (tweenType) {
	            case POSITION_X: x=(newValues[0]); break;
	            case POSITION_Y: y=(newValues[0]); break;
	            case POSITION_XY:
	                    x=(newValues[0]);
	                    y=(newValues[1]);
	                    break;
	            case ROTATION:
	            	r=(newValues[0]);
	                break;
	            case ALPHA:
	            	r=(newValues[0]);
	                break;
	            default: assert false; break;
		    }
		}
	}