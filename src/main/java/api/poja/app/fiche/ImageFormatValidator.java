package api.poja.app.fiche;

import java.util.Set;

public class ImageFormatValidator {

  private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpeg", "jpg", "png");

  public static boolean isValid(String s3Key) {
    if (s3Key == null || !s3Key.contains(".")) return false;
    var extension = s3Key.substring(s3Key.lastIndexOf('.') + 1).toLowerCase();
    return ALLOWED_EXTENSIONS.contains(extension);
  }
}
