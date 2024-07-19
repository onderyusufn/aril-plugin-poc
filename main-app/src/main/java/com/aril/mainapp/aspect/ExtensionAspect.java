package com.aril.mainapp.aspect;

import com.aril.mainapp.utils.MethodUtils;
import com.aril.plugin.annotations.Extendable;
import com.aril.plugin.annotations.process.OverrideProcess;
import com.aril.plugin.annotations.process.PostProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExtensionAspect {
    private final ExtensionManager extensionManager;

    @Before("@annotation(extendable)")
    public void transformExtendable(JoinPoint joinPoint, Extendable extendable) {
        log.info("extendable-id: {}", extendable.id());
        log.info("join-point: {}", joinPoint);
    }

    @Around("@annotation(extendable)")
    public Object overrideOrPostExtendable(ProceedingJoinPoint joinPoint, Extendable extendable) throws Throwable {
        InvokeResult overrideExtensionResult = invokeOverrideExtensionMethod(extendable.id(), joinPoint);
        if (overrideExtensionResult.invoked) {
            return overrideExtensionResult.result;
        }
        InvokeResult postExtensionResult = invokePostExtensionMethod(extendable.id(), joinPoint, overrideExtensionResult);
        if (postExtensionResult.invoked) {
            return postExtensionResult.result;
        }
        //actual process
        return joinPoint.proceed();
    }

    //TODO: refactor with util and remove duplicates
    private InvokeResult invokePostExtensionMethod(String extensionId, ProceedingJoinPoint joinPoint, InvokeResult overrideExtensionResult) throws Throwable {
        Object extensionBean = extensionManager.getBeanByExtensionId(extensionId);
        if (extensionBean == null) {
            return new InvokeResult(false, null);
        }
        //TODO: validation for list-size=1 or 0
        List<Method> postProcessList = MethodUtils.getMethodsAnnotatedWith(extensionBean.getClass(), PostProcess.class);
        if (!CollectionUtils.isEmpty(postProcessList)) {
            Method postMethod = postProcessList.getFirst();
            Object normalProcess = overrideExtensionResult.invoked ? overrideExtensionResult.result : joinPoint.proceed();
            Object response = postMethod.invoke(extensionBean, normalProcess);
            return new InvokeResult(true, response);
        }
        return new InvokeResult(false, null);
    }

    private InvokeResult invokeOverrideExtensionMethod(String extensionId, ProceedingJoinPoint joinPoint) throws Throwable {
        Object extensionBean = extensionManager.getBeanByExtensionId(extensionId);
        if (extensionBean == null) {
            return new InvokeResult(false, null);
        }
        //TODO: validation for list-size=1 or 0
        List<Method> overrideProcessList = MethodUtils.getMethodsAnnotatedWith(extensionBean.getClass(), OverrideProcess.class);
        if (!CollectionUtils.isEmpty(overrideProcessList)) {
            Method overrideMethod = overrideProcessList.getFirst();
            Object response = overrideMethod.invoke(extensionBean, joinPoint.getArgs());
            return new InvokeResult(true, response);
        }
        return new InvokeResult(false, null);
    }

    private record InvokeResult(boolean invoked, Object result) {
    }
}
