package api.poja.app.fiche;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class FicheControllerTest {

  private FicheService ficheService;
  private MockMvc mockMvc;
  private final ObjectMapper om = new ObjectMapper().findAndRegisterModules();

  @BeforeEach
  void setUp() {
    ficheService = mock(FicheService.class);
    mockMvc = MockMvcBuilders.standaloneSetup(new FicheController(ficheService)).build();
  }

  @Test
  void create_returns201() throws Exception {
    var request = new FicheCreationRequest("Fiche 1", "test@test.com", "images/photo.png");
    var dto =
        new FicheDto(
            UUID.randomUUID(), "Fiche 1", "test@test.com", "images/photo.png", Instant.now());
    when(ficheService.create(any())).thenReturn(dto);

    mockMvc
        .perform(
            post("/fiches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }

  @Test
  void findAll_returnsOk() throws Exception {
    when(ficheService.findAll()).thenReturn(List.of());
    mockMvc.perform(get("/fiches")).andExpect(status().isOk());
  }
}
