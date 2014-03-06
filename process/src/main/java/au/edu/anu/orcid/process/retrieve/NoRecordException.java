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
package au.edu.anu.orcid.process.retrieve;

/**
 * <p>NoRecordException</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Exception to indicate that there were no records found for the given request</p>
 *
 * @author Genevieve Turner
 *
 */
public class NoRecordException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param message The error message
	 */
	public NoRecordException(String message) {
		super(message);
	}
	
	/**
	 * Constructor
	 * 
	 * @param message The error message
	 * @param throwable The throwable for the error
	 */
	public NoRecordException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
