package api.poja.app.fiche;

import api.poja.app.endpoint.event.EventProducer;
import api.poja.app.endpoint.event.model.GrayscaleImageRequested;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class FicheService {

  private final FicheRepository ficheRepository;
  private final EventProducer<GrayscaleImageRequested> eventProducer;

  public FicheDto create(FicheCreationRequest request) {
    if (!ImageFormatValidator.isValid(request.s3Key())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Seuls les formats .jpeg/.jpg/.png sont acceptés");
    }

    var fiche =
        Fiche.builder()
            .nomFiche(request.nomFiche())
            .email(request.email())
            .s3Key(request.s3Key())
            .build();

    fiche = ficheRepository.save(fiche);

    eventProducer.accept(
        List.of(
            GrayscaleImageRequested.builder()
                .ficheId(fiche.getId().toString())
                .s3Key(fiche.getS3Key())
                .email(fiche.getEmail())
                .nomFiche(fiche.getNomFiche())
                .build()));

    return FicheDto.from(fiche);
  }

  public List<FicheDto> findAll() {
    return ficheRepository.findAll().stream().map(FicheDto::from).toList();
  }
}
