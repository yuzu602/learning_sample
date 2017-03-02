package sample1;

import java.util.List;

public class SimpleLearning extends ModelUpdater{
	private boolean add;

	SimpleLearning(List<Rule> baserules) {
		super(baserules);
	}

	public void Learning(ActionSet ac){
		add=true;
		for(Rule r:rules){
			if(r.pre.equals(ac.pre)&&r.act.equals(ac.act)){
				for(Condition cond:r.post){
					if(r.post.equals(cond.name)){
						add=false;
					}
				}
			}
		}
		if(add==true){AddRule(ac);}
	}

	private void AddRule(ActionSet ac){
		rules.add(new Rule(ac.pre, ac.act, ac.post));
	}

}