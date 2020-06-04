package com.example.log;

import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;


@Aspect
@Component
@Log4j2
public class Logging {
    @Pointcut("")
    public void callAtMyServicePublic() { }

    @Before("@annotation(LoggingMethod)")
    public void beforeCallAtMethod1(JoinPoint jp) {
        StringBuilder message  = new StringBuilder("Method(): ");
        message.append(jp.getSignature().getName()).append(" ! ");
        Arrays.stream(jp.getArgs()).forEach(arg ->
                message.append("args: ").append(arg).append(" ! "));
        log.info(message.toString());

    }

    /*@After("callAtMyServicePublic()")
    public void afterCallAt(JoinPoint jp) {

    }*/

}
