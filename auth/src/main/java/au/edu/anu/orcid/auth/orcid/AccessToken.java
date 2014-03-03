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
package au.edu.anu.orcid.auth.orcid;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>AccessToken</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Class that deals with access tokens retruned from Orcid OAuth</p>
 *
 * @author Genevieve Turner
 *
 */
@XmlRootElement
public class AccessToken {
	private String accessToken;
	private String tokenType;
	private String orcid;
	private String refreshToken;
	private String expiresIn;
	private String scope;
	
	/**
	 * Constructor
	 */
	public AccessToken() {
		
	}

	/**
	 * Get the access token
	 * 
	 * @return The access token
	 */
	@XmlElement(name="access_token")
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Set the acces token
	 * 
	 * @param accessToken The access token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Get the token type
	 * 
	 * @return The token type
	 */
	@XmlElement(name="token_type")
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * Set the token type
	 * 
	 * @param tokenType The token type
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * Get the orcid
	 * 
	 * @return The orcid
	 */
	@XmlElement(name="orcid")
	public String getOrcid() {
		return orcid;
	}

	/**
	 * Set the orcid
	 * 
	 * @param orcid The orcid
	 */
	public void setOrcid(String orcid) {
		this.orcid = orcid;
	}

	/**
	 * Get the refresh token
	 * 
	 * @return The refresh token
	 */
	@XmlElement(name="refresh_token")
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * Set the refresh token
	 * 
	 * @param refreshToken The refresh token
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * Get how long until it expires
	 * 
	 * @return The time until the token expires
	 */
	@XmlElement(name="expires_in")
	public String getExpiresIn() {
		return expiresIn;
	}

	/**
	 * Set the time until it expires
	 * 
	 * @param expiresIn The time until the token expires
	 */
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	 * Get the scope
	 * 
	 * @return The scope
	 */
	@XmlElement(name="scope")
	public String getScope() {
		return scope;
	}

	/**
	 * Set the scope
	 * 
	 * @param scope The scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
}
