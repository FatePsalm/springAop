package com.solace.springaop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class LogAspects {
    //抽取公共的切入点表达式
    //1、本类引用
    //2、其他的切面引用
    @Pointcut("execution(public int com.solace.springaop.aop.MathCalculator.*(..))")
    public void pointCut() {
    }
    //坏绕通知：需要携带ProceedingJoinPoint类型的参数
    //环绕通知类似于动态代理的全过程：ProceedingJoinPoint类型的参数可以决定是否执行目标方法
    //且环绕通知必须有返回值，返回值即目标方法的返回值。
    @Around(" pointCut()")
    public Object aroundMethod(ProceedingJoinPoint pjd){
        Object result =null;
        String methodName = pjd.getSignature().getName();
        Object args = Arrays.asList(pjd.getArgs());
        Object[] args1 = pjd.getArgs();
        if (Integer.parseInt(args1[1].toString())==0){
            args1[1]=1;
        }

        //执行目标方法
        try {
            //前置通知
            System.out.println("Arround:The method "+methodName +" begins with "+ args);
            result = pjd.proceed(args1);
            //后置通知
            System.out.println("Arround:The method "+ methodName+" ends");
        } catch (Throwable e) {
            e.printStackTrace();
            //异常通知
            System.out.println("Arround:The method "+ methodName+"occurs exception:"+e);
            //throw new RuntimeException(e);
            //不抛出异常的话，异常就被上面抓住，执行下去，返回result，result值为null，转换为int
        }
        //返回通知
        System.out.println("Arround:The method "+ methodName+" ends with the Result "+ result);

        //return 100;
        return result;
    }


    /*  @Around("pointCut()")
    public Object logStart(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("" + joinPoint.getSignature().getName() + "运行。。。@Around:参数列表是：{" + Arrays.asList(args) + "}");
        args[0]=22;
        if (0 == Integer.parseInt( args[1]+"")){
            args[1]=1;
        }
        Object proceed = null;
        try {
            proceed = joinPoint.proceed(args);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }*/
    //@Before在目标方法之前切入；切入点表达式（指定在哪个方法切入）
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println(""+joinPoint.getSignature().getName()+"运行。。。@Before:参数列表是：{"+Arrays.asList(args)+"}");
    }

    @After("com.solace.springaop.aop.LogAspects.pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println(""+joinPoint.getSignature().getName()+"结束。。。@After");
    }

    //JoinPoint一定要出现在参数表的第一位
    @AfterReturning(value="pointCut()",returning="result")
    public void logReturn(JoinPoint joinPoint, Object result){
        System.out.println(""+joinPoint.getSignature().getName()+"正常返回。。。@AfterReturning:运行结果：{"+result+"}");
    }

    @AfterThrowing(value="pointCut()",throwing="exception")
    public void logException(JoinPoint joinPoint,Exception exception){
        System.out.println(""+joinPoint.getSignature().getName()+"异常。。。异常信息：{"+exception+"}");

    }
}
