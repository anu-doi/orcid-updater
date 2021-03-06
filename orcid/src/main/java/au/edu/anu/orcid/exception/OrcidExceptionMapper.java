/*******************************************************************************
 * Australian National University Orcid Updater
 * Copyright (C) 2013  The Australian National University
 * 
 * This file is part of Australian National University Orcid Updater.
 * 
 * Australian National University Orcid Updater is free software: you
 * can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package au.edu.anu.orcid.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.mvc.Viewable;

import au.edu.anu.orcid.auth.orcid.OrcidException;

/**
 * 
 * <p>OrcidExceptionMapper</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>ExceptionMapper class for the OrcidException class.  This either displays a web page with the
 * error message or simply returns the error message with the appropriate status.</p>
 *
 * @author Genevieve Turner
 *
 */
@Provider
public class OrcidExceptionMapper implements ExceptionMapper<OrcidException> {
	
	@Context
	private HttpHeaders headers;
	
	@Context
	private ServletContext context;

	@Override
	public Response toResponse(OrcidException exception) {
		Response resp;
		List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
		
		if (acceptableMediaTypes.contains(MediaType.TEXT_HTML_TYPE)) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("error", exception.getMessage());
			resp = Response.status(Status.BAD_GATEWAY).entity(new Viewable("/jsp/error.jsp", model)).build();
		}
		else {
			resp = Response.status(Status.BAD_GATEWAY).entity(exception.getMessage()).build();
		}
		
		return resp;
	}

}
