package sketchoogiri.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sketchoogiri.domain.mapper.user.UserMapper;
import sketchoogiri.domain.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findByUserId(username);
		if (user == null) {
			throw new UsernameNotFoundException(username + " is not found.");
		}
		return new MyUserDetails(user);
	}

}
