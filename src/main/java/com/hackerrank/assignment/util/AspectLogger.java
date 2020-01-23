package com.hackerrank.assignment.util;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * The AOP component to perform logging.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Slf4j
@Component
@Aspect
public class AspectLogger {


    /**
     * Log method entrance.
     *
     * @param joinPoint the joint point
     */
    @Before("execution(* *(..)) && @annotation(com.hackerrank.assignment.annotations.LogMethod)")
    public void logMethodAccessBefore(JoinPoint joinPoint) {
        String[] parameterNames = ((MethodSignature) (joinPoint.getSignature())).getParameterNames();
        if (parameterNames == null) {
            parameterNames = new String[joinPoint.getArgs().length];
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                parameterNames[i] = "arg" + i;
            }
        }
        AssignmentHelper.logEntrance(joinPoint.getSignature().toString(),
                parameterNames, joinPoint.getArgs());
    }

    /**
     * Log method exit.
     *
     * @param joinPoint the join point
     * @param result    the result
     */
    @AfterReturning(
            pointcut = "execution(* *(..)) && @annotation(com.hackerrank.assignment.annotations.LogMethod)",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        AssignmentHelper.logExit(joinPoint.getSignature().toString(), result);
    }

    /**
     * Log exception.
     *
     * @param joinPoint the joint point
     * @param ex        the exception
     */
    @AfterThrowing(
            pointcut = "execution(* *(..)) && @annotation(com.hackerrank.assignment.annotations.LogMethod)",
            throwing = "ex")
    public void doRecoveryActions(JoinPoint joinPoint, Exception ex) {
        AssignmentHelper.logException(joinPoint.getSignature().toString(), ex);
    }
}
