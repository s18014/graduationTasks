package sketchoogiri.domain.mapper.theme;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import sketchoogiri.domain.model.Theme;

@Mapper
public interface ThemeMapper {
	public Theme findByThemeId(Integer themeId);
	public List<Theme> findByUserId(String userId);
	public List<Theme> findAll();
	public void create(Theme theme);
	public void delete(Integer themeId);
}
