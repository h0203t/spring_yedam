package com.yedam.app.emp.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.emp.service.EmpService;
import com.yedam.app.emp.service.EmpVO;
/**
 * AJAX + JSON + 다양한 클라이언트의 등장 
 * => REST (새로운 형태의 서버)
 *    1) 페이지 없이 순수 데이터만 제공
 *    2) Endpoint(URI+Method)를 구성하는 새로운 방식
 *       URI(자원만 표기) + Method(GET, POST, PUT, DELETE / 기능을 의미)
 */
//@Controller + 모든 메소드에 @ResponseBody를 적용
@RestController
@CrossOrigin
public class EmpRestController {
	private EmpService empService;
	
	public EmpRestController(EmpService empService) {
		this.empService = empService;
	}
/*	
	// 전체조회 : GET
	@GetMapping("empList") 
	public String empList(Model model) {
		List<EmpVO> list = empService.empList();
		model.addAttribute("emps", list);
		return "emp/list";
	}
*/	
	//전체조회 : GET 
	@GetMapping("emps")
	//@ResponseBody // AJAX => Model X
	public List<EmpVO> empList(){
		return empService.empList();	
	}
	
	//단건조회 : GET
	@GetMapping("emps/{employeeId}")
	public EmpVO empInfo(@PathVariable Integer employeeId) {
		EmpVO empVO = new EmpVO();
		empVO.setEmployeeId(employeeId);
		return empService.empInfo(empVO);
	}
	
	//등록 : POST
	@PostMapping("emps") //@RequestBody : JSON
	public int empinsert(@RequestBody EmpVO empVO) {
		return empService.empInsert(empVO);
	}
	
	//수정 : PUT ( <=> POST ) => 전체 데이터
	@PutMapping("emps/{employeeId}")
	public Map<String, Object> empUpdate(@PathVariable Integer employeeId,
											@RequestBody EmpVO empVO){
		empVO.setEmployeeId(employeeId);
		return empService.empUpdate(empVO);
	}
	
	//삭제 : DELETE ( <=> GET )
	@DeleteMapping("emps/{employeeId}")
	public Map<String, Object> empDelete(@PathVariable Integer employeeId){
		return empService.empDelete(employeeId);
	}
}
