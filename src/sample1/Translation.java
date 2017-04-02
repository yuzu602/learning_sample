package sample1;

public class Translation{

	protected static String Monitorable(String moni_act){
		String monitor=null;
		switch(moni_act){
			case "arrive.w":
				monitor="arrive['w]";
				break;
			case "arrive.m":
				monitor="arrive['m]";
				break;
			case "arrive.e":
				monitor="arrive['e]";
				break;
			case "pickupfail":
				monitor="pickupfail";
				break;
			case "pickupsuccess":
				monitor="pickupsuccess";
				break;
			case "putfail":
				monitor="putfail";
				break;
			case "putsuccess":
				monitor="putsuccess";
				break;
		}
		return monitor;
	}

	protected static String Controllable(String c_act){
		String act=null;
		switch(c_act){
			case "move.w":
				act="move['w]";
				break;
			case "move.e":
				act="move['e]";
				break;
			case "pickup":
				act="pickup";
				break;
			case "putdown":
				act="putdown";
				break;
		}
		return act;
	}

	protected static String Map(String pre_act){
		String map=null;
		switch(pre_act){
			case "arrive.w":
				map="MAP['w]";
				break;
			case "arrive.m":
				map="MAP['m]";
				break;
			case "arrive.e":
				map="MAP['e]";
				break;
			case "pickupfail":
				map="MAP['e]";
				break;
			case "pickupsuccess":
				map="MAP['e]";
				break;
			case "putfail":
				map="MAP['w]";
				break;
			case "putsuccess":
				map="MAP['w]";
				break;
		}
		return map;
	}
}