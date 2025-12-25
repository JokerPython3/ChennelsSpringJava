package Reposteryes;

import model.Channels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepo extends JpaRepository<Channels,Long> {
}
