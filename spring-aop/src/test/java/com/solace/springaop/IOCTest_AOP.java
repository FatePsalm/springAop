package com.solace.springaop;

import com.solace.springaop.aop.MathCalculator;
import com.solace.springaop.config.Aop;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_AOP {
	
	@Test
	public void test01(){
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Aop.class);
		
		//1、不要自己创建对象
//		MathCalculator mathCalculator = new MathCalculator();
//		mathCalculator.div(1, 1);
		MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);

		int div = mathCalculator.div(2, 0);
		System.out.println("div:"+div);
		applicationContext.close();
	}

}
