package com.yedam.app.security.service;

import java.util.List;

import lombok.Data;

@Data
public class UserVO {
	private String loginId;
	private String password;
	private List<String> roleName;
	private String fullName;
	private String deptName;
}
