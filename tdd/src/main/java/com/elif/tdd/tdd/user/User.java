package com.elif.tdd.tdd.user;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.elif.tdd.tdd.user.validation.anotation.UniqueUserName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

// this annotation generated getter, setter, constructor, equals and hash code
// methods
@Data
@Entity
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4892065560545754213L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "{tdd.constraints.username.notNull.message}")
	@Size(min = 4, max = 255)
	@UniqueUserName
	private String username;

	@Size(min = 4, max = 255)
	@NotNull
	private String displayName;

	@Size(min = 4, max = 255)
	@NotNull
	private String password;

	private String image;

	@Override
	@Transient // this annotation is used by spring to check the user is active etc.
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_USER");
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@Transient
	public boolean isEnabled() {
		return true;
	}
}
