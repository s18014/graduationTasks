package sketchoogiri.app.test;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import sketchoogiri.domain.mapper.ThemeMapper;
import sketchoogiri.domain.model.Theme;


@Controller
public class TestController {

	@Autowired
	ThemeMapper themeMapper;
	
	@ModelAttribute
	ThemeForm setUpForm() {
		return new ThemeForm();
	}
	
	@GetMapping("/")
	@Transactional
	public String hello(Model model) {
		List<Theme> themeAll = themeMapper.findAll();
		List<Theme> themeOfUser = themeMapper.findByUserId("hentai");
		Theme themeOne = themeMapper.findByThemeId("1");

		model.addAttribute("themeAll", themeAll);
		model.addAttribute("themeOfUser", themeOfUser);
		model.addAttribute("themeOne", themeOne);
		return "hello";
	}
	
	@PostMapping("/")
	@Transactional
	public String create(@Validated ThemeForm themeForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return hello(model);
		}
		Theme newTheme = new Theme();
		newTheme.setUserId("hentai");
		newTheme.setThemeId(UUID.randomUUID().toString());
		newTheme.setRequest(themeForm.getRequest());
		newTheme.setImgUrl("dummy");
		themeMapper.create(newTheme);;
		return "redirect:/";
	}
}
