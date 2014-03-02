package au.edu.anu.orcid.auth.orcid;

public class OAuthException extends Exception {
	private static final long serialVersionUID = 1L;

	public OAuthException(String message) {
		super(message);
	}

	public OAuthException(String message, Throwable throwable) {
		super(message);
	}
}
