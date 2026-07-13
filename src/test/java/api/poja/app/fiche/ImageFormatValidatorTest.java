package api.poja.app.fiche;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class ImageFormatValidatorTest {
  @Test void acceptsPng() { assertThat(ImageFormatValidator.isValid("img/photo.png")).isTrue(); }
  @Test void acceptsJpeg() { assertThat(ImageFormatValidator.isValid("img/photo.jpeg")).isTrue(); }
  @Test void acceptsJpgUpperCase() { assertThat(ImageFormatValidator.isValid("img/photo.JPG")).isTrue(); }
  @Test void rejectsOtherFormat() { assertThat(ImageFormatValidator.isValid("img/photo.gif")).isFalse(); }
  @Test void rejectsNoExtension() { assertThat(ImageFormatValidator.isValid("img/photo")).isFalse(); }
  @Test void rejectsNull() { assertThat(ImageFormatValidator.isValid(null)).isFalse(); }
}
