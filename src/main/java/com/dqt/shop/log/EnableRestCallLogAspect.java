package com.dqt.shop.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class EnableRestCallLogAspect {

    @Around("@annotation(EnableRestCallLogs)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        log.info("Method Signature is {}, Method executed in :{} ms, Input Request:{}, Output Response:{}", joinPoint.getSignature(), System.currentTimeMillis() - startTime, joinPoint.getArgs()[0], proceed);
        return proceed;
    }

//    @Pointcut("@annotation(EnableRestCallLogs)")
//    public void logPointcut(){
//    }
//
//    @Before("logPointcut()")
//    public void logAllMethodCallsAdvice(JoinPoint joinPoint){
//        System.out.println("In Aspect at " + joinPoint.getSignature().getName());
//    }

}
