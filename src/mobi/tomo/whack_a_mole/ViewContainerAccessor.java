package mobi.tomo.whack_a_mole;

import aurelienribon.tweenengine.TweenAccessor;

public class ViewContainerAccessor implements TweenAccessor<ViewContainer> {

  // The following lines define the different possible tween types.
  // It's up to you to define what you need :-)

  public static final int POSITION_X = 1;
  public static final int POSITION_Y = 2;
  public static final int POSITION_XY = 3;
  public static final int ROTATION = 4;
  public static final int SCALE_XY = 5;
  public static final int ALPHA = 6;
  
  public static final ViewContainer viewContainer = new ViewContainer();

  // TweenAccessor implementation

  @Override
  public int getValues(ViewContainer target, int tweenType, float[] returnValues) {
      switch (tweenType) {
          case POSITION_X: returnValues[0] = target.getX(); return 1;
          case POSITION_Y: returnValues[0] = target.getY(); return 1;
          case POSITION_XY:
              returnValues[0] = target.getX();
              returnValues[1] = target.getY();
              return 2;
          case ROTATION:
              returnValues[0] = target.getRotation();
              return 2;
          case SCALE_XY:
              returnValues[0] = target.getScaleXY();
              return 2;
          case ALPHA:
              returnValues[0] = target.getAlpha();
              return 2;
             default: assert false; return -1;
      }
  }
  
  @Override
  public void setValues(ViewContainer target, int tweenType, float[] newValues) {
      switch (tweenType) {
      case POSITION_X: target.setX(newValues[0]); break;
      case POSITION_Y: target.setY(newValues[1]); break;
      case POSITION_XY:
          target.setX(newValues[0]);
          target.setY(newValues[1]);
          break;
      case ROTATION:
    	  target.setRotation(newValues[0]);
    	  break;
      case SCALE_XY:
          target.setScaleXY(newValues[0]);
    	  break;
      case ALPHA:
          target.setAlpha(newValues[0]);
    	  break;
          default: assert false; break;
      }
  }
}