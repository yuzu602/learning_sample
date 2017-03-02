package sample1;

public class Condition{
	// Info.
	protected String name;
	protected double value;

	// For computation
	protected double pre_value;
	protected double grad;
	protected int count;

	Condition(String n){
		this.name=n;
		this.value=0.5;       // Initial value
		this.pre_value=0.5;
		this.grad=0;
		this.count=0;
	}
}