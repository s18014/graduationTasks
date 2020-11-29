package sketchoogiri.app.test;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;


public class ThemeForm implements Serializable {
	@NotBlank(message = "なんか入力して")
	private String request;

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
	
}
