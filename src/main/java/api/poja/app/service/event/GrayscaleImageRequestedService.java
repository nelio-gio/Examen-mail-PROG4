package api.poja.app.service.event;

import api.poja.app.endpoint.event.model.GrayscaleImageRequested;
import api.poja.app.file.bucket.BucketComponent;
import api.poja.app.fiche.ImageGrayscaler;
import api.poja.app.mail.Email;
import api.poja.app.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GrayscaleImageRequestedService implements Consumer<GrayscaleImageRequested> {

  private final BucketComponent bucketComponent;
  private final Mailer mailer;

  @SneakyThrows
  @Override
  public void accept(GrayscaleImageRequested event) {
    File original = bucketComponent.download(event.getS3Key());
    File grayscale = ImageGrayscaler.toGrayscale(original);

    String grayscaleKey = toGrayscaleKey(event.getS3Key());
    bucketComponent.upload(grayscale, grayscaleKey);

    var recipient = new InternetAddress(event.getEmail());
    var presignedUrl = bucketComponent.presign(grayscaleKey, Duration.ofDays(7));
    var htmlBody =
        "<p>Bonjour,</p><p>Voici la version noir et blanc de l'image pour la fiche \""
            + event.getNomFiche()
            + "\" : <a href=\""
            + presignedUrl
            + "\">"
            + presignedUrl
            + "</a></p>";

    mailer.accept(
        new Email(
            recipient, List.of(), List.of(), "Image en noir et blanc", htmlBody, List.of(grayscale)));
  }

  private String toGrayscaleKey(String originalKey) {
    var lastSlash = originalKey.lastIndexOf('/');
    var dir = lastSlash >= 0 ? originalKey.substring(0, lastSlash + 1) : "";
    var fileName = lastSlash >= 0 ? originalKey.substring(lastSlash + 1) : originalKey;
    return dir + "grayscale-" + fileName;
  }
}
