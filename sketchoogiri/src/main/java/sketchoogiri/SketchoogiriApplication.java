package sketchoogiri;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sketchoogiri.domain.mapper.ThemeMapper;
import sketchoogiri.domain.model.Theme;

@SpringBootApplication
@Controller
public class SketchoogiriApplication {
	
	@Autowired
	ThemeMapper themeMapper;
	
	@GetMapping("/")
	@Transactional
	public String hello(Model model) {
		List<Theme> themes = themeMapper.selectAll();
		model.addAttribute("themes", themes);
		return "hello";
	}

	public static void main(String[] args) {
		SpringApplication.run(SketchoogiriApplication.class, args);
	}

}
