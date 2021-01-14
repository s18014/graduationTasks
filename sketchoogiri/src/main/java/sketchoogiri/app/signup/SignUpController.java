package sketchoogiri.app.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import sketchoogiri.domain.mapper.user.UserMapper;
import sketchoogiri.domain.model.User;

@Controller
public class SignUpController {
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@ModelAttribute
	SignUpForm setUpForm() {
		return new SignUpForm();
	}

	@GetMapping("signup")
	public String form(Model model) {
		return "signup-form";
	}
	
	@PostMapping("signup")
	public String register(@Validated SignUpForm signUpForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return form(model);
		}
		User user = new User();
		user.setUserId(signUpForm.getUserId());
		user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
		user.setName(signUpForm.getUserName());
		user.setRole("USER");
		userMapper.create(user);
		return "redirect:/loginForm";
	}
}
