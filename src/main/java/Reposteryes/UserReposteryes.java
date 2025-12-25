package Reposteryes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.User;

public interface UserReposteryes extends JpaRepository<User,Long>{
	Optional<User>findByUsername(String username); 
	Optional<User>findByEmail(String email); 
	

}
