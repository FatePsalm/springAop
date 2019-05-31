package top.lides.template.aop;

import org.springframework.stereotype.Component;

@Component
public class Service {
	@PreAuthority(value="hasRole('admin')")
	public String test(String abc) {
		System.out.println("Service.test:"+abc);
		return abc;
	}
}
