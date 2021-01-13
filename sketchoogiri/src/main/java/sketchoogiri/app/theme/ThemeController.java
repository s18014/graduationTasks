package sketchoogiri.app.theme;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import sketchoogiri.app.sketch.SketchData;
import sketchoogiri.domain.mapper.answer.AnswerMapper;
import sketchoogiri.domain.mapper.theme.ThemeMapper;
import sketchoogiri.domain.mapper.user.UserMapper;
import sketchoogiri.domain.model.Theme;
import sketchoogiri.domain.model.User;
import sketchoogiri.domain.service.storage.StorageService;

@Controller
@RequestMapping("/theme")
public class ThemeController {
	@Autowired
	SketchData sketchData;

	@Autowired
	StorageService storageService;

	@Autowired
	ThemeMapper themeMapper;
	
	@Autowired
	AnswerMapper answerMapper;

	@Autowired
	UserMapper userMapper;

	@ModelAttribute
	ThemeForm setUpForm() {
		return new ThemeForm();
	}
	
	@GetMapping("/{id}")
	public String view(Model model,
			@PathVariable String id) {
		model.addAttribute("theme", themeMapper.findByThemeId(Integer.parseInt(id)));
		model.addAttribute("answers", answerMapper.findByThemeId(Integer.parseInt(id)));
		return "theme";
	}
	
	@GetMapping("/recent")
	public String viewRecent(Model model,
			@RequestParam String page) {
		model.addAttribute("themes", themeMapper.findAll());
		return "themes";
	}

	@GetMapping("/upload")
	public String form(Model model) {
		/*
		String imageBase64;
		if (sketchData.getImage() != null) {
			System.out.println(sketchData.getImage());
			imageBase64 = Base64Utils.encodeToString(sketchData.getImage().getBytes());
			StringBuilder sb = new StringBuilder();
			sb.append("data:");
			sb.append(sketchData.getImage().getContentType());
			sb.append(";base64,");
			sb.append(imageBase64);
			model.addAttribute("image", sb.toString());
		}
		*/
		return "theme-form";
	}

	@PostMapping("/upload")
	public String post(@Validated ThemeForm themeForm,
			BindingResult bindingResult,
			SessionStatus sessionStatus,
			Model model) {
		if (bindingResult.hasErrors()) {
			return form(model);
		}
		MultipartFile image = themeForm.getImage();
		Path path;
		try {
			path = storageService.store(image.getOriginalFilename(), image.getContentType(), image.getBytes());
			Theme theme = new Theme();
			theme.setUserId(dummyUser().getUserId());
			theme.setRequest(themeForm.getRequest());
			theme.setImgUrl("/images/" + path.getFileName().toString());
			themeMapper.create(theme);
			sessionStatus.setComplete();
			return "redirect:/theme/" + theme.getThemeId().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/";
	}

	public User dummyUser() {
		User dummyUser = userMapper.findByUserId("tester1");
		return dummyUser;
	}

}
