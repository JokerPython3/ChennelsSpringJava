package Reposteryes;

import model.RefreshTokens;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenResposteyrs extends JpaRepository<RefreshTokens,Long> {
    Optional<RefreshTokens> findByToken(String token);
    Optional<RefreshTokens> findByUserUsername(Optional<User> user);
    void deleteByUser(User user);
}
