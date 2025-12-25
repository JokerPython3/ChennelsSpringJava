package Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Reposteryes.UserReposteryes;

@Service
public class UserDateilsService implements UserDetailsService {
	@Autowired
	private UserReposteryes userReposteryes;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		model.User user = userReposteryes.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username Not found"));
		return User.withUsername(user.getUsername()).password(user.getPassword()).authorities(user.getRole()).build();
	}

}
