package com.cn.niecl.aspect;

import java.lang.annotation.*;

/**
 * <p>描述: SysLog
 * <p>版权:
 * <p>日期: 2019/11/30 16:03
 * <p>作者: niecl
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
