package sketchoogiri.app.sketch;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sketch")
public class SketchController {
	
	@ModelAttribute
	SketchForm setUpForm() {
		return new SketchForm();
	}

	@GetMapping()
	public String sketch(Model model, @RequestParam("redirect-url") String redirectUrl) {
		model.addAttribute("redirectUrlParam", redirectUrl);
		return "sketch";
	}
	
	@PostMapping()
	public String redirect(RedirectAttributes redirectAttributes, SketchForm sketchForm) {
		redirectAttributes.addFlashAttribute("image", sketchForm.getImage());
		System.out.println(sketchForm.getImage());
		System.out.println(sketchForm.getRedirectUrl());
		return "redirect:" + sketchForm.getRedirectUrl();
	}

}
