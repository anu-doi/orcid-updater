package au.edu.anu.orcid.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import au.edu.anu.orcid.display.RecordIdResource;
import au.edu.anu.orcid.display.RecordUidResource;

@ApplicationPath("/rest")
public class ApplicationConfig extends ResourceConfig {
	public ApplicationConfig() {
		//  Resources
		packages(RecordIdResource.class.getPackage().getName(), RecordUidResource.class.getPackage().getName());
		
		// MVC
		register(JspMvcFeature.class);
		register(new ApplicationBinder());
		// Logging
		//register(LoggingFilter.class);
	}
}
