/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.essms.auth.entities.SystemUser;
import com.essms.auth.entities.UserBranch;
import com.essms.auth.entities.UserRole;
import com.essms.auth.models.CustomUserDetails;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * The Class UserService.
 *
 * @author gaurav
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	/** The Generic Repository. */
	@Autowired
	private GenericRepository genericRepository;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Object> param = new ArrayList<>();
		param.add(username);
		CustomUserDetails customUserDetails = new CustomUserDetails();
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("||username", username);
		criterias.put("||emailId", username);
		criterias.put("||mobileNo", username);
		List<SystemUser> systemUsers = genericRepository.findByCriteria(SystemUser.class, criterias, "createdDate",
				true);
		if (systemUsers == null || systemUsers.isEmpty()) {
			throw new UsernameNotFoundException("User Not Found : " + username);
		}
		SystemUser systemUser = systemUsers.get(0);
		customUserDetails.setUserGUID(systemUser.getGuid());
		customUserDetails.setName(systemUser.getName());
		customUserDetails.setUsername(systemUser.getUsername());
		customUserDetails.setPassword(systemUser.getPassword());
		customUserDetails.setEmailId(systemUser.getEmailId());
		customUserDetails.setMobileNo(systemUser.getMobileNo());
		customUserDetails.setBranchGUID(systemUser.getBranchGUID());
		customUserDetails.setUserType(systemUser.getUserType());
		customUserDetails.setEnabled(systemUser.isEnabled());
		customUserDetails.setAccountNonLocked(systemUser.isAccountNonLocked());
		customUserDetails.setAccountNonExpired(systemUser.isAccountNonExpired());
		customUserDetails.setCredentialsNonExpired(systemUser.isCredentialsNonExpired());
		List<GrantedAuthority> authorities = new ArrayList<>();
		String roleGUIDs = "";
		for (UserRole userRole : systemUser.getUserRoles()) {
			authorities.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
			roleGUIDs += userRole.getRole().getGuid() + ",";
		}
		if (roleGUIDs.length() > 1) {
			roleGUIDs = roleGUIDs.substring(0, roleGUIDs.lastIndexOf(","));
		}
		customUserDetails.setRoleGUIDs(roleGUIDs);
		Map<String, String> allowedBranchGUIDs = new HashMap<>();
		for (UserBranch userBranch : systemUser.getUserBranches()) {
			allowedBranchGUIDs.put(userBranch.getBranch().getGuid(), userBranch.getBranch().getName());
		}
		customUserDetails.setAllowedBranchGUIDs(allowedBranchGUIDs);
		customUserDetails.setAuthorities(authorities);
		systemUser.setLastLoginTime(new Date());
		genericRepository.save(systemUsers.get(0));
		return customUserDetails;
	}
}
