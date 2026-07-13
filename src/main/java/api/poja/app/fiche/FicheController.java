package api.poja.app.fiche;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fiches")
@AllArgsConstructor
public class FicheController {

  private final FicheService ficheService;

  @PostMapping
  public ResponseEntity<FicheDto> create(@Valid @RequestBody FicheCreationRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ficheService.create(request));
  }

  @GetMapping
  public ResponseEntity<List<FicheDto>> findAll() {
    return ResponseEntity.ok(ficheService.findAll());
  }
}
