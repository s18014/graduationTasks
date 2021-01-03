package sketchoogiri.app.answer;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class AnswerForm {
	@NotBlank
	private String description;

	private MultipartFile image;

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}

	
}