package com.yedam.app.upload.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UploadController {
	@Value("${file.upload.path}")
	private String uploadPath;

	// void : 경로를 기반으로 파일을 생성한 경우 사용
	// classpath:/templates/ + URI + .html
	@GetMapping("formUpload")
	public void formUploadPat() {
	}

	@PostMapping("uploadForm")
	public String formUploadFile(@RequestPart MultipartFile file) {
		log.info(file.getContentType()); // 파일의 종류 및 확장자
		log.info(file.getOriginalFilename()); // 파일 이름
		log.info(String.valueOf(file.getSize())); // 파일 크기

		// 1) 저장 경로 설정
		String fileName = file.getOriginalFilename();
		String saveName = uploadPath + File.separator + fileName;
		log.debug("====== saveName : " + saveName);

		// Paths.get() : 특정 경로의 파일 정보를 반환 ( 경로 정의 )
		Path savePath = Paths.get(saveName);

		// 2) 실제 파일 저장
		try {
			file.transferTo(savePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:formUpload";
	}

	@GetMapping("upload")
	public void uploadPage() {}

	@PostMapping("/uploadsAjax")
	@ResponseBody // AJAX
	public List<String> uploadFile(@RequestPart MultipartFile[] uploadFiles) {

		List<String> imageList = new ArrayList<>();

		for (MultipartFile uploadFile : uploadFiles) {
			if (uploadFile.getContentType().startsWith("image") == false) {
				System.err.println("this file is not image type");
				return null;
			}

			String fileName = uploadFile.getOriginalFilename();

			System.out.println("fileName : " + fileName);

			// 날짜 폴더 생성 : 파일을 관리하기 위해 폴더별로 분리
			String folderPath = makeFolder();
			// UUID : 파일명이 중복되는 부분 방지
			String uuid = UUID.randomUUID().toString();
			
			// 저장할 파일 이름 중간에 "_"를 이용하여 구분			
			String uploadFileName = folderPath + File.separator + uuid + "_" + fileName;

			String saveName = uploadPath + File.separator + uploadFileName;

			Path savePath = Paths.get(saveName);

			System.out.println("path : " + saveName);
			try {
				uploadFile.transferTo(savePath);

			} catch (IOException e) {
				e.printStackTrace();
			}
			// DB에 해당 경로 저장
			// 1) 사용자가 업로드할 때 사용한 파일명
			// 2) 실제 서버에 업로드할 때 사용한 경로
			imageList.add(setImagePath(uploadFileName));
		}

		return imageList;
	}

	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		// LocalDate를 문자열로 포멧
		String folderPath = str.replace("/", File.separator);
		File uploadPathFoler = new File(uploadPath, folderPath);
		// File newFile= new File(dir,"파일명");

		// 해당 경로의 존재유무를 확인
		if (uploadPathFoler.exists() == false) {
			// mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
			uploadPathFoler.mkdirs();
		}
		return folderPath;
	}

	private String setImagePath(String uploadFileName) {
		return uploadFileName.replace(File.separator, "/");
	}
}
