package com.yedam.app.emp.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.emp.service.EmpService;
import com.yedam.app.emp.service.EmpVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor // 생성자 주입의 변경여부를 고려
						 // => 추가 생성자 X
public class EmpController {
	// 제공하고자 하는 서비스
	private final EmpService empService;
	// GET : 조회, 빈페이지
	// POST : 데이터 조작(등록, 수정, 삭제)
	
	// 전체조회 : GET
	@GetMapping("empList") // 사용자의 요청(URI + METHOD)
	public String empList(Model model) {
		// 기능 수행 
		List<EmpVO> list = empService.empList();
		// => 페이지에 전달할 데이터 담기
		model.addAttribute("emps", list);
		return "emp/list"; // 데이터를 출력할 페이지
		//classpath:/templates/emp/list.html
	}
	
	// 단건조회 : GET => QueryString + 객체 -> 커맨드 객체
	@GetMapping("empInfo")
	public String empInfo(EmpVO empVO, Model model) {
						// 커맨드 객체 : 객체 + 어노테이션 X
		EmpVO findVO = empService.empInfo(empVO);
		
		model.addAttribute("emp", findVO);
		return "emp/info";
	}
	
	// 등록 - 페이지 : GET
	@GetMapping("empInsert")
	public String empInsertForm() {
		return "emp/insert";
	}
	
	// 등록 - 처리   : POST => HTML의 <form/>태그 + submit
	//                    -> QueryString + 객체 / 커맨드 객체
	@PostMapping("empInsert")
	public String empInsertProcess(EmpVO empVO) {
		int eid = empService.empInsert(empVO);
		
		String url = null;
		if(eid > -1) {
			// 정상적으로 등록
			url = "redirect:empInfo?employeeId="+eid;
		}else {
			// 등록되지 않은 경우
			url = "redirect:empInsert";
		}
		return url;
	}
	
	// 수정 - 페이지 : GET <=> 단건조회
	@GetMapping("empUpdate")
	public String empUpdate(EmpVO empVO, Model model) {
						// 커맨드 객체 : 객체 + 어노테이션 X
		EmpVO findVO = empService.empInfo(empVO);
		
		model.addAttribute("emp", findVO);
		return "emp/update";
	}
	// 수정 - 처리   : POST
	// 1) AJAX => QueryString
	//@PostMapping("empUpdate")
	@ResponseBody // AJAX => Model 객체 X
	public Map<String, Object>
			empupdateAJAXQueryString(EmpVO empVO){
		return empService.empUpdate(empVO);
	}
	
	// 2) AJAX => JSON ( @RequestBody )
	@PostMapping("empUpdate")
	@ResponseBody // AJAX => Model 객체 X
	public Map<String, Object>
			empupdateAJAXJSON(@RequestBody EmpVO empVO){
		return empService.empUpdate(empVO);
	}
	// 삭제 : GET => QueryString + 단일값 -> @ResquestParam
	@GetMapping("empDelete")
	public String empDelete(Integer empId) {
		empService.empDelete(empId);
		return "redirect:empList";
	}
	
	
	
	
}
