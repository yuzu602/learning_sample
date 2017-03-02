package sample1;

import java.util.ArrayList;
import java.util.List;

public class Rule {
	public int count;
	public String pre;
	public String act;
	public List<Condition> post;

	Rule(String pre_act, String c_act, String post_act){
		this.pre=pre_act;
		this.act=c_act;
		post = new ArrayList<Condition>();
		post.add(new Condition(post_act));
	}
}