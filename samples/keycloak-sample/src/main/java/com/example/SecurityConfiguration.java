package com.example;

import static org.springframework.security.extensions.saml2.config.SAMLConfigurer.saml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Value("${hostname.preferred}")
    String hostnamePreferred;

	@Value("${security.saml2.metadata-url}")
	String metadataUrl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    if (hostnamePreferred == null || hostnamePreferred.isEmpty()) {
	        hostnamePreferred = "localhost:8443";
        }
		http
			.authorizeRequests()
				.antMatchers("/saml/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.apply(saml())
				.serviceProvider()
					.keyStore()
						.storeFilePath("saml/keystore.jks")
						.password("secret")
						.keyname("spring")
						.keyPassword("secret")
						.and()
					.protocol("https")
					.hostname(hostnamePreferred)
					.basePath("/")
					.and()
				.identityProvider()
					.metadataFilePath(metadataUrl)
					.and();
	}
}
