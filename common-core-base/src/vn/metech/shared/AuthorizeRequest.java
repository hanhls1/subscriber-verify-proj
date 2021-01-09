package vn.metech.shared;

public class AuthorizeRequest {

	private String path;
	private String token;
	private String remoteAddr;

	public AuthorizeRequest() {
	}

	public AuthorizeRequest(String path, String token) {
		this.path = path;
		this.token = token;
	}

	public AuthorizeRequest(String path, String token, String remoteAddr) {
		this(path, token);
		this.remoteAddr = remoteAddr;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
}
