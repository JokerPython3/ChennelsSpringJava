package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Data
@Table(name = "channels")
@Getter
@Setter
public class Channels {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String name;
    @Column( nullable = false)
    private String description;
    private LocalTime now = LocalTime.now();
}
