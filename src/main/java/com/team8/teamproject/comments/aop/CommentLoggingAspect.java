package com.team8.teamproject.comments.aop;


import com.team8.teamproject.comments.exception.CommentNotFoundException;
import com.team8.teamproject.comments.exception.PostNotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class CommentLoggingAspect {

    private Logger log = LoggerFactory.getLogger(CommentLoggingAspect.class);

    public void setLogger(Logger logger) {
        this.log = logger;
    }

    @Pointcut("execution(* * com.team8.teamproject.comments.service.*(..))")
    public void targetMethod(){

    }

    @AfterThrowing(pointcut = "execution(* * com.team8.teamproject.comments.service.*(..))", throwing = "ex")
    public void logFindPostAfterThrowing(JoinPoint joinPoint, PostNotFoundException ex){
        log.error("[예외 발생] 메서드: {}, 에러 메시지: {}", joinPoint.getSignature().toShortString(), ex.getMessage());
    }

    @AfterThrowing(pointcut = "execution(* * com.team8.teamproject.comments.service.*(..))", throwing = "ex")
    public void logFindCommentAfterThrowing(JoinPoint joinPoint, CommentNotFoundException ex){
        log.error("[예외 발생] 메서드: {}, 에러 메시지: {}", joinPoint.getSignature().toShortString(), ex.getMessage());
    }
}
