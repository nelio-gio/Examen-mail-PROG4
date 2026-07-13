package api.poja.app.fiche;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "fiche")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fiche {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String nomFiche;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String s3Key;

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @PrePersist
  void onCreate() {
    if (createdAt == null) createdAt = Instant.now();
  }
}
