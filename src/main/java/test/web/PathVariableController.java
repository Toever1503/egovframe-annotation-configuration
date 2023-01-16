package test.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/path/api/{id}")
public class PathVariableController {

	public PathVariableController() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("path variable controller");
	}

	@GetMapping("/page/{title}")
	public String getPageById(@PathVariable String id, @PathVariable String title) {
		return id + title;
	}
}
