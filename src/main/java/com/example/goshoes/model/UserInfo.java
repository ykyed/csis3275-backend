package com.example.goshoes.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="userInfo")
public class UserInfo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String role;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String dob;  

	public UserInfo() {}
    
    public UserInfo(String email, String password, String role, String firstName, String lastName, String dob) {
    	this.email = email;
    	this.password = password;
    	this.role = role;
    	this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	 public String getRole() {
			return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	 public String getFirstName() {
	        
		 return firstName;
	 }

	 public void setFirstName(String firstName) {
	       
	    	this.firstName = firstName; 
	 }

	 public String getLastName() {
	        return lastName; 
	    }

	 public void setLastName(String lastName) {
	        this.lastName = lastName; 
	    }

	 public String getDob() {
	        return dob; 
	    }

	 public void setDob(String dob) {
	        this.dob = dob; 
	    }
	
	public Collection<SimpleGrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.role));
	}
}
