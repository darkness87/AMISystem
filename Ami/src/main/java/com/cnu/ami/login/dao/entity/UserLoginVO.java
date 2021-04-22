package com.cnu.ami.login.dao.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class UserLoginVO implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "UID")
	private String userid;

	@Column(name = "UPW")
	private String password;

	@Column(name = "UTYPE")
	private String type;

	@Column(name = "UNAME")
	private String name;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "POSITION")
	private String position;

	@Column(name = "LEVEL")
	private int level; // 0:관리자, 1:운영자, 2:사용자

//	@Column(name = "GSEQ")
//	private String gSeq;
//
//	@Column(name = "GNAME")
//	private String gName;

	@Column(name = "WDATE")
	private long regDate;

	@Column(name = "LDATE")
	private long updateDate;

	@Transient
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<>();

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
