package sample1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



public class GenerateDomainModel {
	private static final String domainName="Domain.txt";
	protected static List<Rule> rules;              // List of rules
	private static List<Rule> sub_rules;          // List of rules (with the same pre-condition)

	protected static void GenerateDomainText(List<Rule> r){
		rules=new ArrayList<Rule>(r);
		File file = new File(domainName);
		file.delete();
		WriteModel();
	}

	/* Write FSP */
	protected static void WriteModel(){
		Rule r;
		try{
			File file = new File(domainName);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			while(true){
				sub_rules=new ArrayList<Rule>();
				r=rules.get(0);
				sub_rules.add(rules.remove(0));
				pw.println(Translation.Map(r.pre)+"=");
				pw.print("(");
				for(int i=0;i<rules.size();i++){
					if(Comp_pre(rules.get(i),r)){
						sub_rules.add(rules.remove(i));
						i--;
					}
				}
				WriteAction(pw);
				if(rules.size()==0){
					pw.println(".");
					break;
				}else{
					pw.println(",");
				}
			}
			pw.close();
		}catch(IOException e){
			System.out.println(e);
		}
	}

	/* Write Controllable, Monitorable Actions */
	private static void WriteAction(PrintWriter pw){
		boolean first=true;

		//PrepareWrite();

		for(Rule r:sub_rules){
			if(first==true){
				pw.print(Translation.Controllable(r.act)+" -> ");
				first=false;
			}else{
				pw.print("|"+Translation.Controllable(r.act)+" -> ");
			}
			for(int i=0;i<r.post.size();i++){
					if(i==0){
						pw.print("("+Translation.Monitorable(r.post.get(0).name)+" -> "+Translation.Map(r.post.get(0).name));
					}else{
						pw.print("|"+Translation.Monitorable(r.post.get(0).name)+" -> "+Translation.Map(r.post.get(0).name));
					}
			}
			pw.print(")");
			pw.println();
		}
		pw.print(")");
	}

	/*private static void PrepareWrite(){
		Rule x;
		int count;
		for(int i=0;i<sub_rules.size();i++){
			x=sub_rules.get(i);
			count=0;
			for(int j=i+1;j<sub_rules.size();j++){
				if(Comp_pre(x,sub_rules.get(j))){
					for(Condition c:sub_rules.get(j).post){
						sub_rules.get(i).post.add(new Condition(c.name));
					}
					sub_rules.remove(j);
					j--;
					count++;
				}
			}
			i=i-count;
		}
	}*/

	private static boolean Comp_pre(Rule a, Rule b){
		return Translation.Map(a.pre).equals(Translation.Map(b.pre));
	}
	private static boolean Comp_c(Rule a, Rule b){
		return Translation.Controllable(a.act).equals(Translation.Controllable(b.act));
	}
}