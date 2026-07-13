package api.poja.app.fiche;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FicheCreationRequest(
    @NotBlank String nomFiche, @NotBlank @Email String email, @NotBlank String s3Key) {}
