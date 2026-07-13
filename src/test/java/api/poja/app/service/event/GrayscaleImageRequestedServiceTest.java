package api.poja.app.service.event;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import api.poja.app.endpoint.event.model.GrayscaleImageRequested;
import api.poja.app.file.bucket.BucketComponent;
import api.poja.app.mail.Mailer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

class GrayscaleImageRequestedServiceTest {

  private final BucketComponent bucketComponent = mock(BucketComponent.class);
  private final Mailer mailer = mock(Mailer.class);
  private final GrayscaleImageRequestedService service =
      new GrayscaleImageRequestedService(bucketComponent, mailer);

  @Test
  void accept_downloadsConvertsUploadsAndSendsEmail() throws Exception {
    var image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
    var file = File.createTempFile("test-", ".png");
    ImageIO.write(image, "png", file);

    when(bucketComponent.download(any())).thenReturn(file);
    when(bucketComponent.presign(any(), any()))
        .thenReturn(new URL("https://example.com/image.png"));

    var event =
        GrayscaleImageRequested.builder()
            .ficheId("id-1")
            .s3Key("images/photo.png")
            .email("test@test.com")
            .nomFiche("Fiche 1")
            .build();

    service.accept(event);

    verify(bucketComponent).upload(any(File.class), any());
    verify(mailer).accept(any());
  }
}
