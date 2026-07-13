package api.poja.app.fiche;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FicheRepository extends JpaRepository<Fiche, UUID> {}
