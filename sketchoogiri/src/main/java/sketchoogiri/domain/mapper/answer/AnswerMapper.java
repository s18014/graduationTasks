package sketchoogiri.domain.mapper.answer;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import sketchoogiri.domain.model.Answer;

@Mapper
public interface AnswerMapper {
	public Answer findByAnswerId(Integer answerId);
	public List<Answer> findByUserId(String userId);
	public List<Answer> findByThemeId(Integer themeId);
	public List<Answer> findAll();
	public void create(Answer answer);
	public void delete(Integer themeId);

}
