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
package au.edu.anu.orcid.metadatastores.misc;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p>MSFORSubject</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Field of research object for unmarshalling XML from metadata stores</p>
 *
 * @author Genevieve Turner
 *
 */
public class MSFORSubject {
	private String code;
	private String percentage;
	private String value;

	/**
	 * Get the field of research code
	 * 
	 * @return The code
	 */
	@XmlElement(name="code")
	public String getCode() {
		return code;
	}

	/**
	 * Set the field of research code
	 * 
	 * @param code The code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get the field of research weighting percentage
	 * 
	 * @return The percentage
	 */
	@XmlElement(name="percentage")
	public String getPercentage() {
		return percentage;
	}

	/**
	 * Set the field of research weighting percentage
	 * 
	 * @param percentage The percentage
	 */
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	/**
	 * Get the field of resaerch value
	 * 
	 * @return The value
	 */
	@XmlElement(name="value")
	public String getValue() {
		return value;
	}

	/**
	 * Set the field of research value
	 * 
	 * @param value The value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
