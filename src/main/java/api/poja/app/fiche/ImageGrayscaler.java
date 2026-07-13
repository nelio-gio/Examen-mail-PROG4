package api.poja.app.fiche;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import lombok.SneakyThrows;

public class ImageGrayscaler {

  @SneakyThrows
  public static File toGrayscale(File source) {
    var original = ImageIO.read(source);
    var grayscale =
        new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

    for (int x = 0; x < original.getWidth(); x++) {
      for (int y = 0; y < original.getHeight(); y++) {
        var rgb = original.getRGB(x, y);
        var color = new Color(rgb, true);
        var gray =
            (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
        grayscale.setRGB(x, y, new Color(gray, gray, gray).getRGB());
      }
    }

    var extension = source.getName().substring(source.getName().lastIndexOf('.') + 1);
    var output = File.createTempFile("grayscale-", "." + extension);
    ImageIO.write(grayscale, extension.equalsIgnoreCase("png") ? "png" : "jpg", output);
    return output;
  }
}
