package sketchoogiri.app.signup;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

public class SignUpForm implements Serializable {
	@NotEmpty
	private String userId;
	@NotEmpty
	private String userName;
	@NotEmpty
	private String password;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
