package sketchoogiri.app.theme;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;


public class ThemeForm implements Serializable {
	@NotBlank
	private String request;
	// TODO: MultipartFile Not Empty Validation
	private MultipartFile image;

	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
}
