package com.yedam.app.aop.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.app.aop.mapper.AaaMapper;
import com.yedam.app.aop.service.AaaService;

import lombok.RequiredArgsConstructor;

@Service // AOP가 적용될 대상
@RequiredArgsConstructor
public class AaaServiceImpl implements AaaService{
	private final AaaMapper aaaMapper;

	@Transactional
	@Override
	public void insert() {
		aaaMapper.aaaInsert("101");
		aaaMapper.aaaInsert("a101");
	}
}
