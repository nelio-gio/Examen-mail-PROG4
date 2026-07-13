package api.poja.app.fiche;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import api.poja.app.endpoint.event.EventProducer;
import api.poja.app.endpoint.event.model.GrayscaleImageRequested;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class FicheServiceTest {

  private final FicheRepository ficheRepository = mock(FicheRepository.class);
  private final EventProducer<GrayscaleImageRequested> eventProducer = mock(EventProducer.class);
  private final FicheService ficheService = new FicheService(ficheRepository, eventProducer);

  @Test
  void create_rejectsInvalidFormat() {
    var request = new FicheCreationRequest("Fiche 1", "test@test.com", "images/photo.gif");
    assertThatThrownBy(() -> ficheService.create(request))
        .isInstanceOf(ResponseStatusException.class);
    verifyNoInteractions(ficheRepository, eventProducer);
  }

  @Test
  void create_savesAndPublishesEvent() {
    var request = new FicheCreationRequest("Fiche 1", "test@test.com", "images/photo.png");
    var savedFiche =
        Fiche.builder()
            .id(UUID.randomUUID())
            .nomFiche("Fiche 1")
            .email("test@test.com")
            .s3Key("images/photo.png")
            .build();
    when(ficheRepository.save(any())).thenReturn(savedFiche);

    var result = ficheService.create(request);

    assertThat(result.nomFiche()).isEqualTo("Fiche 1");
    verify(eventProducer).accept(any());
  }

  @Test
  void findAll_mapsToDto() {
    when(ficheRepository.findAll())
        .thenReturn(
            List.of(
                Fiche.builder()
                    .id(UUID.randomUUID())
                    .nomFiche("A")
                    .email("a@a.com")
                    .s3Key("a.png")
                    .build()));

    assertThat(ficheService.findAll()).hasSize(1);
  }
}
