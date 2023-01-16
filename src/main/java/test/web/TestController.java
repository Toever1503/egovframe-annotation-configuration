package test.web;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import test.config.db.PageableProxy;
import test.repositories.UserRepository;

@RestController
@RequestMapping("/upload")
public class TestController {

	@Autowired
	@Lazy
	private PageableProxy pageableProxy;
	
	public TestController(EntityManagerFactory emf, UserRepository userRepository) {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("upload controller created");
		System.out.println(userRepository.getName("test1"));
		System.out.println(userRepository.findByUsername("test2"));
	}

	@RequestMapping("test")
	public Object test() {
		System.out.println("page request ->: " + this.pageableProxy.getPageable());
		return this.pageableProxy.getPageable().toString();
	}

	@PostMapping("file")
	public String uploadFile(@RequestParam(name = "file") MultipartFile file) throws IOException, ServletException {
		return "upload file size: " + file.getOriginalFilename();
	}
}
