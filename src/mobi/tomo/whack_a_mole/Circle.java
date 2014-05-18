package mobi.tomo.whack_a_mole;

//Circle.java 
//描画用に、draw(Canvas canvas)メソッドを用意しています。
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import aurelienribon.tweenengine.*;
import aurelienribon.tweenengine.Tween;

	public class Circle {
		protected Paint paint;
		//Tween.registerAccessor(Particle.class, new ParticleAccessor());
		//----------------------------
		//プロパティ
		public float x;
		public float y;
		public float r;
		//----------------------------
		//Tween用定数
		public static final short X = 1;
		public static final short Y = 2;
		public static final short XY = 3;
		public static final short R = 4;
		
		
		public interface  TweenAccessor<Particle>{
		    public int getValues(Particle target, int tweenType, float[] returnValues);
		    public void setValues(Particle target, int tweenType, float[] newValues);
		}
		
	 
		public Circle()
	    {
			paint = new Paint();
			paint.setColor(Color.WHITE);
			x=200;
			y=200;
			r =40;
	    }
	 
		/**
		 * 描画
		 * @param canvas
		 */
		public void draw(Canvas canvas)
		{
			canvas.drawCircle(x, y, r, paint);
		}
		
	     public <Particle> int getValues(Particle target, int tweenType, float[] returnValues) {
	         switch (tweenType) {
	             case X: returnValues[0] = 40; return 1;
	             case Y: returnValues[1] = 40; return 1;
	             case XY:
	                 returnValues[0] = 40;
	                 returnValues[1] = 40;
	                 return 2;
	             default: assert false; return 0;
	         }
	     }
	     
	     public <Particle> void setValues(Particle target, int tweenType, float[] newValues) {
	         switch (tweenType) {
	             case X: newValues[0] = 40; break;
	             case Y: newValues[1] = 40; break;
	             case XY:
	            	 newValues[0] = 40;;
	            	 newValues[1] = 40;;
	                 break;
	             default: assert false; break;
	         }
	     }

	}