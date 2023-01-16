package test.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import test.repositories.UserRepository;

@RestController
@RequestMapping("/upload")
public class TestController {

	public TestController(UserRepository userRepository) {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("upload controller created");
		System.out.println("test repo: "+ userRepository.toString());
		SimpleJpaRepository<T, ID>
	}

	@RequestMapping("test")
	public Object test(Pageable page) {
		return page;
	}

	@PostMapping("file")
	public String uploadFile(@RequestParam(name = "file") MultipartFile file) throws IOException, ServletException {
		return "upload file size: " + file.getOriginalFilename();
	}
}
