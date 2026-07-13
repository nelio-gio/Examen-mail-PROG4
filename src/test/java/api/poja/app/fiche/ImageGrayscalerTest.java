package api.poja.app.fiche;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

class ImageGrayscalerTest {
  @Test
  void convertsToGrayscale() throws Exception {
    var original = new BufferedImage(4, 4, BufferedImage.TYPE_INT_RGB);
    original.setRGB(0, 0, 0xFF0000);
    var file = File.createTempFile("test-", ".png");
    ImageIO.write(original, "png", file);

    var result = ImageGrayscaler.toGrayscale(file);

    assertThat(result).exists();
    assertThat(ImageIO.read(result).getWidth()).isEqualTo(4);
  }
}
