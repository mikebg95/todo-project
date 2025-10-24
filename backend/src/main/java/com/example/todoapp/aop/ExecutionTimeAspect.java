package com.example.todoapp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("@within(com.example.todoapp.aop.LogExecutionTime) ||@annotation(com.example.todoapp.aop.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object result = joinPoint.proceed(); // run the method
        long durationMs = (System.nanoTime() - start) / 1_000_000;

        log.info("{} took {} ms", joinPoint.getSignature(), durationMs);

        return result;
    }
}
