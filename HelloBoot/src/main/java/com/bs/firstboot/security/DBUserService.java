package com.bs.firstboot.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.bs.firstboot.common.mapper.MemberMapper;
import com.bs.firstboot.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DBUserService implements UserDetailsService{
	
	private final MemberMapper dao;
	private final BCryptPasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member loginMember=dao.selectMemberById(username);
		return loginMember;
	}

	
	
}
