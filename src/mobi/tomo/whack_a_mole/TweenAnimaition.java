package mobi.tomo.whack_a_mole;

public class TweenAnimaition {
	float c;
	float t;
	float b;
	float d;
	
	public void init() {
		c = 0;
		t = 0;
		b = 0;
		d = 0;
		
	}

	//simple linear tweening - no easing, no acceleration
	public float linearTween(float t, float b, float c, float d) {
		return c*t/d + b;
	}
			
	// quadratic easing in - accelerating from zero velocity
	public float easeInQuad(float t, float b, float c, float d) {
		t /= d;
		return c*t*t + b;
	}
			
	// quadratic easing out - decelerating to zero velocity
	public float easeOutQuad(float t, float b, float c, float d) {
		t /= d;
		return -c * t*(t-2) + b;
	}

	// quadratic easing in/out - acceleration until halfway, then deceleration
	public float easeInOutQuad(float t, float b, float c, float d) {
		t /= d/2;
		if (t < 1) return c/2*t*t + b;
		t--;
		return -c/2 * (t*(t-2) - 1) + b;
	}

	// cubic easing in - accelerating from zero velocity
	public float easeInCubic(float t, float b, float c, float d) {
		t /= d;
		return c*t*t*t + b;
	}

	// cubic easing out - decelerating to zero velocity
	public float easeOutCubic(float t, float b, float c, float d) {
		t /= d;
		t--;
		return c*(t*t*t + 1) + b;
	}

	// cubic easing in/out - acceleration until halfway, then deceleration
	public double easeInOutCubic(float t, float b, float c, float d) {
		t /= d/2;
		if (t < 1) return c/2*t*t*t + b;
		t -= 2;
		return c/2*(t*t*t + 2) + b;
	}
	
	// quartic easing in - accelerating from zero velocity
	public float easeInQuart(float t, float b, float c, float d) {
		t /= d;
		return c*t*t*t*t + b;
	}

	// quartic easing out - decelerating to zero velocity
	public float easeOutQuart(float t, float b, float c, float d) {
		t /= d;
		t--;
		return -c * (t*t*t*t - 1) + b;
	}
			
	// quartic easing in/out - acceleration until halfway, then deceleration
	public float easeInOutQuart(float t, float b, float c, float d) {
		t /= d/2;
		if (t < 1) return c/2*t*t*t*t + b;
		t -= 2;
		return -c/2 * (t*t*t*t - 2) + b;
	}

	// quintic easing in - accelerating from zero velocity
	public float easeInQuint(float t, float b, float c, float d) {
		t /= d;
		return c*t*t*t*t*t + b;
	}

	// quintic easing out - decelerating to zero velocity
	public float easeOutQuint(float t, float b, float c, float d) {
		t /= d;
		t--;
		return c*(t*t*t*t*t + 1) + b;
	}

	// quintic easing in/out - acceleration until halfway, then deceleration
	public float easeInOutQuint(float t, float b, float c, float d) {
		t /= d/2;
		if (t < 1) return c/2*t*t*t*t*t + b;
		t -= 2;
		return c/2*(t*t*t*t*t + 2) + b;
	}
			
	// sinusoidal easing in - accelerating from zero velocity
	public double easeInSine(float t, float b, float c, float d) {
		return -c * Math.cos(t/d * (Math.PI/2)) + c + b;
	}

	// sinusoidal easing out - decelerating to zero velocity
	public double easeOutSine(float t, float b, float c, float d) {
		return c * Math.sin(t/d * (Math.PI/2)) + b;
	}

	// sinusoidal easing in/out - accelerating until halfway, then decelerating
	public double easeInOutSine(float t, float b, float c, float d) {
		return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b;
	}

	// exponential easing in - accelerating from zero velocity
	public double easeInExpo(float t, float b, float c, float d) {
		return c * Math.pow( 2, 10 * (t/d - 1) ) + b;
	}

	// exponential easing out - decelerating to zero velocity
	public double easeOutExpo(float t, float b, float c, float d) {
		return c * ( -Math.pow( 2, -10 * t/d ) + 1 ) + b;
	}

	// exponential easing in/out - accelerating until halfway, then decelerating
	public double easeInOutExpo(float t, float b, float c, float d) {
		t /= d/2;
		if (t < 1) return c/2 * Math.pow( 2, 10 * (t - 1) ) + b;
		t--;
		return c/2 * ( -Math.pow( 2, -10 * t) + 2 ) + b;
	}

	// circular easing in - accelerating from zero velocity
	public double easeInCirc(float t, float b, float c, float d) {
		t /= d;
		return -c * (Math.sqrt(1 - t*t) - 1) + b;
	}

	// circular easing out - decelerating to zero velocity
	public double easeOutCirc(float t, float b, float c, float d) {
		t /= d;
		t--;
		return c * Math.sqrt(1 - t*t) + b;
	}

	// circular easing in/out - acceleration until halfway, then deceleration
	public double easeInOutCirc(float t, float b, float c, float d) {
		t /= d/2;
		if (t < 1) return -c/2 * (Math.sqrt(1 - t*t) - 1) + b;
		t -= 2;
		return c/2 * (Math.sqrt(1 - t*t) + 1) + b;
	}

}
