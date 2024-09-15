package com.eazybyts.crm.config;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.eazybyts.crm.entity.AppUser;
import com.eazybyts.crm.repository.AppUserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUser appUser = appUserRepository.findByEmailId(email);
		if(Objects.isNull(appUser))	{
			throw new UsernameNotFoundException("User not found!");
		}
		return new CustomUserDetails(appUser.getEmailId(), 
				appUser.getPassword(), appUser.getUserRole().name());
	}

}
