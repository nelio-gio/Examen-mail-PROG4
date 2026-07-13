package api.poja.app.fiche;

import api.poja.app.file.bucket.BucketComponent;
import java.io.File;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class DebugUploadController {

  private final BucketComponent bucketComponent;

  @SneakyThrows
  @PostMapping("/debug/upload-image")
  public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
    var extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
    var tempFile = File.createTempFile("upload-", extension);
    file.transferTo(tempFile);

    var s3Key = "images/" + System.currentTimeMillis() + extension;
    bucketComponent.upload(tempFile, s3Key);

    return ResponseEntity.ok(s3Key);
  }
}
