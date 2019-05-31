package com.solace.springaop.aop;

public class MathCalculator {
	
	public int div(int i,int j){
		System.out.println("MathCalculator...div...i:"+i+",j:"+j);
		return i/j;	
	}

}
