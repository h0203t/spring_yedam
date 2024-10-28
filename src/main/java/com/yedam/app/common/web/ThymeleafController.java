package com.yedam.app.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedam.app.emp.service.EmpService;
import com.yedam.app.emp.service.EmpVO;

@Controller
public class ThymeleafController {
	private EmpService empServcie;
	
	public ThymeleafController(EmpService empService) {
		this.empServcie = empService;
	}
	
	@GetMapping("firstPage")
	public String firstPage() {
		return "thymeleaf/first";
		// prefix => classpath:/templates/
		// suffix => .html
		// classpath:/templates/thymeleaf/first.html
	}
	
	@GetMapping("secondPage")
	public String secondPage(Model model) {
		EmpVO empVO = new EmpVO();
		empVO.setEmployeeId(100);
		
		EmpVO findVO = empServcie.empInfo(empVO);
		model.addAttribute("info", findVO);
		return "thymeleaf/second";
		// classpath:/templates/thymeleaf/second.html
	}
	
}
