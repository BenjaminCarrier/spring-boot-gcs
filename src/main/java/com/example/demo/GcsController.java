package com.example.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.io.OutputStream;
import org.springframework.core.io.WritableResource;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class GcsController {

  @Value("gs://spring-bucket-benjamin_carrier/my-file.txt")
  private Resource gcsFile;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String readGcsFile() throws IOException {
    return StreamUtils.copyToString(
        gcsFile.getInputStream(),
        Charset.defaultCharset()) + "\n";
  }

  @RequestMapping(value = "/", method = RequestMethod.POST)
  String writeGcs(@RequestBody String data) throws IOException {
    try (OutputStream os = ((WritableResource) gcsFile).getOutputStream()) {
      os.write(data.getBytes());
    }
    return "file was updated\n";
  }
}