package api.poja.app.fiche;

import java.time.Instant;
import java.util.UUID;

public record FicheDto(UUID id, String nomFiche, String email, String s3Key, Instant createdAt) {

  public static FicheDto from(Fiche fiche) {
    return new FicheDto(
        fiche.getId(), fiche.getNomFiche(), fiche.getEmail(), fiche.getS3Key(), fiche.getCreatedAt());
  }
}
