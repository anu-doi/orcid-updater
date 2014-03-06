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
package au.edu.anu.orcid.security;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import au.edu.anu.orcid.util.PropertyLoader;

/**
 * <p>SecurityConfig</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Configures the methods and restrictions for logging in</p>
 *
 * @author Genevieve Turner
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@ImportResource("classpath:/applicationContext.xml")
@ComponentScan("au.edu.anu.orcid")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		Properties properties = PropertyLoader.loadProperties("ldap.properties");
		LdapUserDetailsMapper contextMapper = new LdapUserDetailsMapper();
		contextMapper.setRoleAttributes(new String[]{"affiliation"});
		contextMapper.setPasswordAttributeName("password");
		
		String ldapUri = properties.getProperty("ldap.uri");
		String baseDn = properties.getProperty("ldap.baseDn");
		
		String contextLdapUri = ldapUri + "/" + baseDn;
		
		LOGGER.debug("Ldap Url: {}", contextLdapUri);
		
		auth.ldapAuthentication()
			.userDnPatterns("uid={0}, ou=People")
				.contextSource()
					.url(contextLdapUri)
					.and()
				.userDetailsContextMapper(contextMapper);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().permitAll()
				.and()
			.formLogin().permitAll()
				.loginPage("/login")
				.successHandler(new OrcidAuthenticationSuccessHandler())
				.and()
			.logout().logoutSuccessUrl("/")
				.and()
			.httpBasic();
	}
}
