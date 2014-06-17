package mobi.tomo.whack_a_mole;

public class TweenAnimation {
	float t;
	float d;
	float c;
	float b;
	
	
	static double easeOutCirc(float t,float b,double c,float d) {
		t /= d;
		t--;
		return c * Math.sqrt(1 - t*t) + b;
	}

}
