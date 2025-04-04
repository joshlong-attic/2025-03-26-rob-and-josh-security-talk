package testjars.service;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/")
	String hello(Principal principal) {
		return "Hello " + principal.getName() + "!";
	}
}
