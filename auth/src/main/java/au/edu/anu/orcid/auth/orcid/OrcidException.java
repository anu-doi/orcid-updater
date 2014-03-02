package au.edu.anu.orcid.auth.orcid;

public class OrcidException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public OrcidException(String message) {
		super(message);
	}
	
	public OrcidException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
