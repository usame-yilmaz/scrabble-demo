package com.scrabble.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

        private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

        @Pointcut("execution(* com.scrabble.controller..*(..))")
        public void resource()
        {
            // nothing to do
        }

        @Around("resource()")
        public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable
        {
            long startTime = System.currentTimeMillis();
            logger.info("Api called: {}.{}({}) -> duration : {} ms", 
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    joinPoint.getArgs(), 
                    System.currentTimeMillis() - startTime);
            return joinPoint.proceed();
        }

        @AfterThrowing(pointcut = "resource()", throwing = "exception")
        public void logAfterThrowing(JoinPoint joinPoint, Throwable exception)
        {
            logger.error("Exception  {} ", joinPoint.getSignature().getName());
            logger.error("Cause : {} ", exception.getMessage(), exception);
        }
    }