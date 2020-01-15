package com.scrabble.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ValidationAspect {
	@Autowired
	private ApplicationContext applicationContext;

	
	/**
	 * Pointcut for running business validations.
	 *
	 * @param call
	 *            the execution stack join point
	 * @throws Throwable
	 *             on failure
	 */

	@Around("@annotation(com.scrabble.aop.ScrabbleValidator)")
	public Object validate(ProceedingJoinPoint call) throws Throwable {
		Class controllerObject = call.getTarget().getClass();		
		
		MethodSignature methodSignature = (MethodSignature) call.getSignature();
		methodSignature.getDeclaringType();
		methodSignature.getParameterNames();
		Method method = controllerObject.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
		Class validatorClass = readAnnotation(method);
		if (validatorClass != null) {			
				Validator validator = (Validator) applicationContext.getBean(validatorClass);
				//Check if validator supports related controller
				if (validator.supports(controllerObject)) {		
					//Get the parameter name and values of the method. add them as key value pairs so that they can be used by validator.
					Map<String, Object> parameterNameValueMap = getNameValuePairs(call);
					MapBindingResult mapBindingResult = new MapBindingResult(parameterNameValueMap, call.getTarget().getClass().getName());
					
					ValidationUtils.invokeValidator(validator, call.getTarget(), mapBindingResult);
					if (mapBindingResult.hasErrors()) {
						throw new RuntimeException(mapBindingResult.getAllErrors().stream().findAny().get().toString());
					}
				}
		}

		return call.proceed();
	
	}


	/**
	 * Read annotation to get validation class detail.
	 */
	private Class readAnnotation(AnnotatedElement element) {
		Class validatorClass = null;
		try {
			Annotation[] classAnnotations = element.getAnnotations();
			for (Annotation annotation : classAnnotations) {
				if (annotation instanceof com.scrabble.aop.ScrabbleValidator) {
					ScrabbleValidator validator = (ScrabbleValidator) annotation;
					validatorClass = validator.validator();
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return validatorClass;
	}

	
	private Map<String,Object> getNameValuePairs(JoinPoint joinPoint) {
        Map<String,Object> arguments = new HashMap<String,Object>();
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature(); 
        String[] names = codeSignature.getParameterNames();
        Object[] values = joinPoint.getArgs();
        for (int i = 0; i < values.length; i++)
            arguments.put(names[i], values[i]);
        return arguments;
    }

	
}
