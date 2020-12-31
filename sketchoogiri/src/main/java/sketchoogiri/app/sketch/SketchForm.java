package sketchoogiri.app.sketch;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class SketchForm implements Serializable {

	private MultipartFile image;
	
	private String redirectUrl;

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
}
