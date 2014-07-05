package mobi.tomo.whack_a_mole;


public class ObjVariable {

	Variable variable = new Variable();
	
	int objX;
	int objY;
	float objScale;
	long objMaxTime;
	long objNowTime;
	long objStatTime;
	long objPassedTime;

	public void objUpdate() {
		//時間の更新のみ
		objPassedTime = System.currentTimeMillis() - objStatTime;
	}
}
