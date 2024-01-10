package com.bs.firstboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bs.firstboot.model.dto.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ViewTestController {

	@Value("${linux.home}")
	private String homeDir;
	@Value("${linux.url}")
	private String url;
	
	@Value("${window.javahome}")
	private String path;
	
	@GetMapping("/valuetest")
	@ResponseBody
	public List<String> ymlData(){
		log.debug(homeDir);
		log.debug(url);
		log.debug(path);
		return List.of(homeDir,url,path);
	}
	
	@PostMapping("/fileupload")
	public String uploadfile(MultipartFile[] upFile) {
		for(MultipartFile f : upFile) {
			log.debug(f.getOriginalFilename());
			log.debug("크기 : {}",f.getSize());
		}
		
		return "redirect:/";
	}
	
	
	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("name", "bslove");
		
		Member m=Member.builder()
				.name("장세영").age(28).address("경기도 광명시")
				.gender("F").build();
		List<String> team=List.of("김선진","서진","박대웅","박승현");
		model.addAttribute("team",team);
		model.addAttribute("member",m);
		
		return "index";
	}
//	
	@GetMapping("/test")
	public String test() {
		return "user/test";
	}
	
	@GetMapping("/test2")
	@ResponseBody
	public String test2(String data) {
		return data+"왔어 왔어";
	}
//	
//	@GetMapping("/board/boardlist")
//	public String boardmain() {
//		return "board/boardlist";
//	}
	
	
}
