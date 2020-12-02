package sketchoogiri.app.top;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sketchoogiri.domain.mapper.theme.ThemeMapper;
import sketchoogiri.domain.mapper.user.UserMapper;
import sketchoogiri.domain.model.Theme;
import sketchoogiri.domain.model.User;

@Controller
public class TopPageController {
	
	@Autowired
	ThemeMapper themeMapper;
	
	@Autowired
	UserMapper UserMapper;

	@GetMapping
	public String test(Model model) {
		List<Theme> themeAll = themeMapper.findAll();
		List<User> userAll = UserMapper.findAll();
		model.addAttribute("themeAll", themeAll);
		model.addAttribute("userAll", userAll);
		return "test";
	}
}
