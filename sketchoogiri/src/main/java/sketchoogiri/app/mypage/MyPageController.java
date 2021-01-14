package sketchoogiri.app.mypage;

import java.util.List;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sketchoogiri.domain.mapper.theme.ThemeMapper;
import sketchoogiri.domain.mapper.user.UserMapper;
import sketchoogiri.domain.model.Theme;
import sketchoogiri.domain.model.User;
import sketchoogiri.domain.service.user.MyUserDetails;



@Controller
@RequestMapping("/mypage")
public class MyPageController {
	
	@Autowired
	ThemeMapper themeMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@GetMapping
	public String mypage(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
		User loginUser = myUserDetails.getUser();
		List<Theme> themes = themeMapper.findByUserId(loginUser.getUserId());
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("themes", themes);
		return "mypage";
	}
	
	public User dummyUser() {
		User dummyUser = userMapper.findByUserId("tester1");
		return dummyUser;
	}
}