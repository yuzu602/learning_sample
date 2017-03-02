package sample1;


public class SGDCalculation {
	private static Rule rule;
	private static double MSE;     // Value of objective function
	private static String obs;     // Observed data
	private static double base;    // For computation
	private static int num;        // For computation

	private static final double e=0.1;  // Learning rate

	public static Rule Calc(Rule r, String observed_data){
		rule=r;
		obs=observed_data;
		num=0;

		for(Condition cond:rule.post){
			if(obs.equals(cond.name)){break;}
			num++;
		}
		StochasticGradientDescent();
		return rule;
	}

	/*  Stochastic Gradient Descent  */
	private static void StochasticGradientDescent(){
		double sum=0;
		Base();
		for(Condition cond:rule.post){  // Compute grad.
			cond.grad=Grad(cond);
		}
		for(Condition cond:rule.post){  // Update rule
			cond.value-=e*cond.grad;
			if(cond.value<0){cond.value=0;}
			sum+=cond.value;
		}
		for(Condition cond:rule.post){
			cond.value=cond.value/sum;
		}
		MSE=MSE();
	}

	/*  Compute Gradient  */
	private static double Grad(Condition cond){
		double grad, pb;

		pb=Probabilistic();
		if(obs.equals(cond.name)){
			grad=GradP_in(cond,pb);
		}else{
			grad=GradP_ex(pb);
		}
		return grad;
	}

	/*  Partial Differential */
	private static double GradP_in(Condition cond, double pb){
		return 2*(1-pb)*(-1)*(base-cond.value)/(base*base);
	}

	/*  Partial Differentia */
	private static double GradP_ex(double pb){
		return 2*(1-pb)*(-1)*(-1*rule.post.get(num).value)/(base*base);
	}

	/*  Denominator of P(x|r)  */
	private static void Base(){
		base=0;
		for(Condition cond: rule.post){
			base+=cond.value;
		}
	}

	/*  P(x|r) of each Time  */
	private static double Probabilistic(){
		return rule.post.get(num).value/base;
	}

	/*  MSE  */
	private static double MSE(){
		double pb;
		pb=Probabilistic();
		return Math.pow(1-pb,2);
	}
}
