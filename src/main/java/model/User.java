package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Data
public class User {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	@Column(unique = true,length = 20)
	private String username;
	@Column(unique = false,length = 400)
	private String password;
	@Column(unique = true,length = 30)
	private String email;
	@Column(unique = false)
	private String name;
	@Column(name= "role")
	private String role;
	@OneToMany(
			mappedBy="user",cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<RefreshTokens> refreshTokensList = new ArrayList<>();
	@CreationTimestamp
	private java.util.Date timestamp;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
