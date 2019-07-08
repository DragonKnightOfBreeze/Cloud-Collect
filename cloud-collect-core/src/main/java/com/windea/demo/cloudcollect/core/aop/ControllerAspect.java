package com.windea.demo.cloudcollect.core.aop;

import com.windea.demo.cloudcollect.core.exception.ValidationException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.validation.BindingResult;

/**
 * 控制器的切面。
 */
@Aspect
public class ControllerAspect {
	/**
	 * 参数验证的切面（在bindingResult有错时，抛出ValidationException）。
	 */
	@Before("within(com.windea.demo.cloudcollect.core.controller..*) && args(bindingResult, ..)")
	public void bindingResultAdvice(BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors());
		}
	}
}
