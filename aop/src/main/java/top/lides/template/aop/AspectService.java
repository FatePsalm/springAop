package top.lides.template.aop;


import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectService {
	
/*	@Pointcut("execution(public * top.lides.template.aop.AOPController.*(..))")
	public void pointcut() {};*/
	/**
	 * 通过注解切入
	 * 只有@Around可以修改目标方法的接收参数和返回值
	 * @param joinpoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(top.lides.template.aop.PreAuthority)")
	public Object around(ProceedingJoinPoint joinpoint) throws Throwable {
		System.out.println("Around增强：执行方法之前，模拟开始事务");
		Object[]objects = joinpoint.getArgs();
		for(int i = 0;i < objects.length;i++) {
			if(objects[i] instanceof String) {
				System.out.println("开始增强----------------");
				objects[i] = "增强参数:"+objects[i];					
			}
		}
		//获得切入点 的对象
		Object object = joinpoint.getTarget();
		//获得切入点的方法名
		String name = joinpoint.getSignature().getName();
		Class zlass = object.getClass();
		Method[]methods = zlass.getDeclaredMethods();
		for (Method method : methods) {
			if(method.getName().equals(name)) {
				PreAuthority annotation = method.getAnnotation(PreAuthority.class);
				String value = annotation.value();
				//判断是获取角色还是权限
				if(value.matches("hasRole\\('.*'\\)")) {
					value = value.replaceAll("(hasRole\\('|'\\))", "");
				}else if(value.matches("hasPermission\\('.*'\\)")) {
					value = value.replaceAll("(hasPermission\\('|'\\))", "");
				}else {
					throw new IllegalArgumentException("权限表达式错误：hasRole('role')|hasPermission('permission')");
				}
			}
		}
		//切入点方法的返回值
		Object rvt = joinpoint.proceed(objects);
		  System.out.println("Around增强：执行方法之后，模拟结束事务");
		  if(rvt instanceof String) {
			  rvt = "增强返回值："+(String)rvt;
		  }
		return rvt;
	}
	/**
	 * 通过切入点切入
	 * @param joinpoint
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
/*	@Before("pointcut()")
	public void point(JoinPoint joinpoint) throws InstantiationException, IllegalAccessException {
		
	}*/
}
