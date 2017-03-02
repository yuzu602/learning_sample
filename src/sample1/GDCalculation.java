package sample1;


public class GDCalculation {
	private static Rule rule;
	private static double time;
	private static final double calc_count=10000;    // Limited learning count
	private static final double dif_th=1.0e-14;      // Threshold of Amount of dif.

	private static double MSE;                       // For computation
	private static double pre_MSE;                   // For computation
	private static double base;                      // For computation

	private static final double e2=0.1;       // Learning rate

	protected static Rule Calc(Rule r){
		rule=r;
		time=CalcTime();
		pre_MSE=10;
		if(time!=0){
			GradientDescent();
		}
		return rule;
	}

	/*  Gradient Descent  */
	private static void GradientDescent(){
		double sum;
		for(int i=0;i<calc_count;i++){
			Base();
			sum=0;

			for(Condition cond:rule.post){
				cond.grad=Grad(cond);
			}
			for(Condition cond:rule.post){
				cond.value-=e2*cond.grad;
				if(cond.value<0){cond.value=0;}
				sum+=cond.value;
			}
			for(Condition cond:rule.post){
				cond.value=cond.value/sum;
			}
			MSE=MSE();
			if(Math.abs(pre_MSE-MSE) <= dif_th){
				break;
			}
			pre_MSE=MSE;
			for(Condition cond: rule.post){
				cond.pre_value=cond.value;
			}
		}
	}

	/*  Compute Gradient */
	private static double Grad(Condition param){
		double grad;
		double sum=0;
		double pb;

		for(Condition cond:rule.post){
			pb=Probabilistic(cond);
			for(int j=0;j<cond.count;j++){
				if(cond.name==param.name){
					sum+=GradP_in(param,pb);
				}else{
					sum+=GradP_ex(param,pb);
				}
			}
		}
		grad=sum/time;
		return grad;
	}

	/*  Partial Differential  */
	private static double GradP_in(Condition cond, double pb){
		return 2*(1-pb)*(-1)*(base-cond.value)/(base*base);
	}

	/*  Partial Differential  */
	private static double GradP_ex(Condition cond, double pb){
		return 2*(1-pb)*(-1)*(-1*cond.value)/(base*base);
	}

	/*  Denominator of P(x|r)  */
	private static void Base(){
		base=0;
		for(Condition cond: rule.post){
			base+=cond.value;
		}
	}

	/*  P(x|r) of each Time  */
	private static double Probabilistic(Condition cond){
		return cond.value/base;
	}

	/*  MSE  */
	private static double MSE(){
		double sum=0;
		double pb;
		for(Condition cond:rule.post){
			pb=Probabilistic(cond);
			for(int j=0;j<cond.count;j++){
				sum+=Math.pow(1-pb,2);
			}
		}
		return sum/time;
	}

	private static double CalcTime(){
		double sum=0;
		for(Condition cond: rule.post){
			sum+=cond.count;
		}
		return sum;
	}
}
