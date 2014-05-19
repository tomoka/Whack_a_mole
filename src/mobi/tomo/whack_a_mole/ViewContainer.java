package mobi.tomo.whack_a_mole;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ViewContainer {
  private float x,y,r,s,a;
  public View view;
  
  public float getX() {
      return x;
  }
  
  public float getY() {
      return y;
  }
  public float getRotation() {
	  return r;
  }
  public float getScaleXY() {
	  return s;
  }
  public float getAlpha() {
	  return a;
  }

  public void setX(float x) {
      this.x = x;
      
      /**
       * ATTENTION FOR Android less than 3.0 do this.
       * Why Relative? Because our cointainer in RelativeContainer
       */
      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
      params.leftMargin= (int)x;
      view.setLayoutParams(params);
      
      /**
       * IF FOR android 3.0 and more 
       */
      view.setX(x);
     
  }

  public void setY(float y) {
      this.y = y;
     
      /**
       * ATTENTION FOR Android less than 3.0 do this.
       */
      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
      params.topMargin = (int)y;
      view.setLayoutParams(params);
      
      
      /**
       * IF FOR android 3.0 and more 
       */
      view.setY(y);
      
     
  }
  public void setRotation(float r) {
	  this.r = r;
	  
      /**
       * IF FOR android 3.0 and more 
       */
      
      view.setRotation(r);
	}
  public void setScaleXY(float s) {
	  this.s = s;
	  
      /**
       * IF FOR android 3.0 and more 
       */
      
      view.setScaleX(s);
      view.setScaleY(s);
	}
  public void setAlpha(float a) {
	  this.a = a;
	  
      /**
       * IF FOR android 3.0 and more 
       */
      
      view.setAlpha((float) a);
	}
}