package com.cn.niecl.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>描述: SysLogAspect
 * <p>版权:
 * <p>日期: 2019/11/30 16:05
 * <p>作者: niecl
 */
@Aspect
@Component
public class SysLogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(SysLogAspect.class);

//    @Pointcut(value = "@annotation(com.cn.niecl.aspect.SysLog)")
    @Pointcut("execution(* com.cn.niecl.controller..*.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void beginTransaction() {
        System.out.println("before beginTransaction");
    }

    @After("pointcut()")
    public void commit() {
        System.out.println("after commit");
    }

    @AfterReturning(value = "pointcut()", returning = "returnObject")
    public void afterReturning(JoinPoint joinPoint, Object returnObject) {
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing rollback");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        LOG.info(" Around 执行方法前");
        //执行方法
        Object result = point.proceed();
        LOG.info("Around 执行方法后");
        return result;
    }
}
