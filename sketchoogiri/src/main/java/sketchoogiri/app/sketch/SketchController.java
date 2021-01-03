package sketchoogiri.app.sketch;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sketchoogiri.common.utils.UploadedFile;

@Controller
@RequestMapping("/sketch")
public class SketchController {
	@Autowired
	SketchData sketchData;

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
		MultipartFile image = sketchForm.getImage();
		if (image != null) {
			try {
				sketchData.setImage(
						new UploadedFile(image.getOriginalFilename(), image.getContentType(), image.getBytes()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(sketchForm.getImage());
		System.out.println(sketchForm.getRedirectUrl());
		return "redirect:" + sketchForm.getRedirectUrl();
	}

}
