package sketchoogiri.app.theme;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sketchoogiri.domain.mapper.theme.ThemeMapper;
import sketchoogiri.domain.mapper.user.UserMapper;
import sketchoogiri.domain.model.Theme;
import sketchoogiri.domain.model.User;
import sketchoogiri.domain.service.storage.StorageService;

@Controller
@RequestMapping("/theme")
public class ThemeController {

	@Autowired
	StorageService storageService;
	
	@Autowired
	ThemeMapper themeMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@ModelAttribute
	ThemeForm setUpForm() {
		return new ThemeForm();
	}
	
	@GetMapping("/upload")
	public String form(Model model) {
		return "theme-form";
	}

	@PostMapping("/upload")
	public String post(@Validated ThemeForm themeForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return form(model);
		}
		Path path = storageService.store(themeForm.getImage());
		Theme theme = new Theme();
		theme.setUserId(dummyUser().getUserId());
		theme.setRequest(themeForm.getRequest());
		theme.setImgUrl("/images/" + path.getFileName().toString());
		themeMapper.create(theme);
		return "redirect:/mypage";
	}
	
	public User dummyUser() {
		User dummyUser = userMapper.findByUserId("hentai");
		return dummyUser;
	}

}
