package sketchoogiri.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import sketchoogiri.domain.model.Theme;

@Mapper
public interface ThemeMapper {
	public List<Theme> selectAll();
}
