package top.lides.template.aop;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AOPController {
	@Resource
	Service demo;
	@GetMapping("/aop")
	public void test() {
		System.out.println(demo.test("123"));		
	}
}
