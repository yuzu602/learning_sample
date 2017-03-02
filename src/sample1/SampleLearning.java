package sample1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class SampleLearning {
	public static ModelUpdater mu;
	public static List<ActionSet> traces = new ArrayList<ActionSet>();
	private static String rulesFileName="BaseRules.txt";   // Prepared rules
	private static String traceFileName="Traces.txt";      // Prepared traces
	private static String outputFileName="Result.csv";     // Updated Rules

	public static void main(String[] args) {
		//mu = new GDLearning(GetBaseRules());
		mu = new SGDLearning(GetBaseRules());
		//mu = new SimpleLearning(GetBaseRules());
		SampleFromFile();
	}

	public static void SampleFromFile(){
		GetTraces();
		while(traces.size()!=0){
			mu.Learning(traces.remove(0));
		}
		PrintResult(mu.rules);
	}

	private static List<Rule> GetBaseRules(){
		String[] line;
		String str;
		boolean addR;
		List<Rule> allrules = new ArrayList<Rule>();

		try{
			File file = new File(rulesFileName);
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((str = br.readLine())!=null){   // Check the trace
				line=new String[3];
				line=str.split(",",0);
				addR=true;
				for(Rule r:allrules){
					if(r.pre.equals(line[0])&&r.act.equals(line[1])){
						r.post.add(new Condition(line[2]));
						addR=false;
					}
				}
				if(addR==true){
					allrules.add(new Rule(line[0],line[1],line[2]));
				}
			}
			br.close();
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}

		return allrules;
	}

	private static void GetTraces(){
		String str;
		String pre, act, post;   // ActionSet<pre,act,post>
		try{
			File file = new File(traceFileName);
			BufferedReader br = new BufferedReader(new FileReader(file));
			post=br.readLine();
			while((str = br.readLine())!=null){
				pre=post;
				act=str;
				post=br.readLine();
				traces.add(new ActionSet(pre,act,post));
			}
			br.close();
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
	}

	private static void PrintResult(List<Rule> rules){
		try {
			File file = new File(outputFileName);
			PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for(Rule r:rules){
				for(Condition c:r.post){
					w.println(r.pre+","+r.act+","+c.name+","+c.value);
				}
			}
			w.close();
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
	}
}
