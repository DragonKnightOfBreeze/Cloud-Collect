package com.windea.demo.cloudcollect.core.aop

import com.windea.demo.cloudcollect.core.exceptions.*
import org.aspectj.lang.*
import org.aspectj.lang.annotation.*
import org.springframework.stereotype.*
import org.springframework.validation.*

/**控制器的切面。*/
@Component
@Aspect
class ControllerAspect {
	/**参数验证的切面。在bindingResult有错时，抛出ValidationException。*/
	@Before("execution(* com.windea.demo.cloudcollect.core.controller..*(..,org.springframework.validation.BindingResult,..))")
	fun validationAdvice(joinPoint: JoinPoint) {
		//NOTE：当需要匹配任意位置的参数时，将类型写在execution表达式里面，而非将参数名写在args表达式里面
		joinPoint.args.filterIsInstance<BindingResult>().forEach { bindingResult ->
			if(bindingResult.hasErrors()) {
				throw ValidationException(bindingResult.allErrors)
			}
		}
	}
}
