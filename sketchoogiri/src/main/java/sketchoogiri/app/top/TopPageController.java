package sketchoogiri.app.top;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopPageController {
	@GetMapping
	public String hello() {
		return "hello";
	}
}
