package sample1;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelUpdater {
	public static List<Rule> rules;

	ModelUpdater(List<Rule> baserules){
		rules = new ArrayList<Rule>(baserules);
	}

	public abstract void Learning(ActionSet ac);
}