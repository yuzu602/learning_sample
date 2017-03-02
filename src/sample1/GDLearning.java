package sample1;

import java.util.ArrayList;
import java.util.List;

public class GDLearning extends ModelUpdater{
	private static final int traceSize = 3001;
	private static List<String> basetraces, traces;   // For record of traces
	//private static long start, end;                 // For time measurement

	GDLearning(List<Rule> baserules) {
		super(baserules);
		basetraces = new ArrayList<String>();
	}

	public void Learning(ActionSet ac){
		GenerateTrace(ac);
		//start=System.nanoTime();
		ReadTrace();
		Calculation();
		//end=System.nanoTime();
	}

	private static void GenerateTrace(ActionSet ac){
		if(basetraces.size()==traceSize){
			basetraces.remove(0);
			basetraces.remove(0);
		}
		basetraces.add(ac.act);
		basetraces.add(ac.post);
		traces = new ArrayList<String>(basetraces);
	}

	private static void ReadTrace(){
		String pre, act, post;
		post=traces.remove(0);
		while(traces.size()!=0){
			pre=post;
			act=traces.remove(0);
			post=traces.remove(0);
			for(Rule r:rules){
				if(r.pre.equals(pre)&&r.act.equals(act)){
					for(Condition cond:r.post){
						if(cond.name.equals(post)){
							cond.count++;
						}
					}
					break;
				}
			}
		}
	}

	private static void Calculation(){
		for(Rule r: rules){
			r=GDCalculation.Calc(r);
		}
	}
}
