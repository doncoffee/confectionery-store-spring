package by.academy.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class CustomLoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Controller *)" +
            " || within(@org.springframework.stereotype.Service *)")
    public void loggingTargets() {
    }

    @Before("loggingTargets())")
    public void logBeforeExecution(JoinPoint joinPoint) {
        log.info("executing method: " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName() + " with arguments " + Arrays.asList(joinPoint.getArgs()));
    }

    @AfterReturning(value = "loggingTargets()", returning = "value")
    public void logAfterExecution(JoinPoint joinPoint, Object value) {
        log.info("method " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName() + " executed and return value [" + value + "]");
    }

    @AfterThrowing(value = "loggingTargets()", throwing = "exception")
    public void logAfterException(JoinPoint joinPoint, Exception exception) {
        log.error("method " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName() + " threw " + exception);
    }
}