package test.web;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import test.repositories.UserRepository;

@RestController
@RequestMapping("/upload")
public class TestController {

	public TestController(EntityManagerFactory emf, UserRepository userRepository) {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("upload controller created");
		System.out.println(userRepository.findByUsername("test1"));
		System.out.println(userRepository.findByUsername("test2"));
		System.out.println(userRepository.findById(3l));
	}

	@RequestMapping("test")
	public Object test(@Qualifier("pageCustom") @Autowired Pageable page) {
		return page;
	}

	@PostMapping("file")
	public String uploadFile(@RequestParam(name = "file") MultipartFile file) throws IOException, ServletException {
		return "upload file size: " + file.getOriginalFilename();
	}
}
