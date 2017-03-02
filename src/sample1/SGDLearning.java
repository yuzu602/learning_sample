package sample1;

import java.util.List;

public class SGDLearning extends ModelUpdater{
	//private static long start,end;  // For time measurement

	SGDLearning(List<Rule> baserules) {
		super(baserules);
	}

	public void Learning(ActionSet ac){
		//start=System.nanoTime();
		for(Rule r:rules){
			if(r.pre.equals(ac.pre)&&r.act.equals(ac.act)){
				r=SGDCalculation.Calc(r,ac.post);
				//end=System.nanoTime();
				RecordPreValue();
				break;
			}
		}
	}

	private static void RecordPreValue(){
		for(Rule r:rules){
			for(Condition cond:r.post){
				cond.pre_value=cond.value;
			}
		}
	}
}
