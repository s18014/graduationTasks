package sketchoogiri.app.answer;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import sketchoogiri.app.sketch.SketchData;
import sketchoogiri.domain.mapper.answer.AnswerMapper;
import sketchoogiri.domain.mapper.theme.ThemeMapper;
import sketchoogiri.domain.mapper.user.UserMapper;
import sketchoogiri.domain.model.Answer;
import sketchoogiri.domain.model.User;
import sketchoogiri.domain.service.storage.StorageService;

@Controller
@RequestMapping("/answer")
public class AnswerController {
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
	AnswerForm setUpForm() {
		return new AnswerForm();
	}
	
	@GetMapping("/{id}")
	public String view(Model model,
			@PathVariable String id) {
		Answer answer = answerMapper.findByAnswerId(Integer.parseInt(id));
		model.addAttribute("answer", answer);
		model.addAttribute("theme", themeMapper.findByThemeId(answer.getThemeId()));
		return "answer";
	}
	
	@GetMapping("/recent")
	public String viewRecent(Model model, @RequestParam String page) {
		model.addAttribute("answers", answerMapper.findAll());
		return "answers";
	}

	@GetMapping("/upload")
	public String form(Model model, @RequestParam("theme") String id) {
		model.addAttribute("theme", themeMapper.findByThemeId(Integer.parseInt(id)));
		return "answer-form";
	}

	@PostMapping("/upload")
	public String upload(@Validated AnswerForm answerForm,
			BindingResult bindingResult,
			Model model,
			@RequestParam("theme") String id) {
		if (bindingResult.hasErrors()) {
			return form(model, id);
		}
		MultipartFile image = answerForm.getImage();
		Path path;
		try {
			path = storageService.store(image.getOriginalFilename(), image.getContentType(), image.getBytes());
			Answer answer = new Answer();
			answer.setUserId(dummyUser().getUserId());
			answer.setThemeId(Integer.parseInt(id));
			answer.setDescription(answerForm.getDescription());
			answer.setImgUrl("/images/" + path.getFileName().toString());
			answerMapper.create(answer);
			return "redirect:/theme/" + id;
		} catch (IOException e) {
			e.printStackTrace();
		}
	

		return "redirect:/";
	}

	public User dummyUser() {
		User dummyUser = userMapper.findByUserId("tester1");
		return dummyUser;
	}
}
