package sketchoogiri.domain.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import sketchoogiri.domain.model.User;

@Mapper
public interface UserMapper {
	public User findByUserId(String userId);
	public List<User> findAll();
	public void create(User user);
}
