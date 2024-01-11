package br.tec.gtech.utilities.toggler.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.tec.gtech.utilities.toggler.api.domain.FeatureFlag;
import br.tec.gtech.utilities.toggler.api.domain.FeatureFlagRequestDTO;


@RestController
@RequestMapping("/api/v1/featuretoggle")
public class FeatureToggleController {

  private static final String JSON_EXTENSION = ".json";
  private static final String NEXT_LINE = "\n";
  private static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private String readFromInputStream(InputStream inputStream)
      throws IOException {
    if (null == inputStream) {
      throw new IllegalArgumentException("Unable to find valid FeatureFlag definition file");
    }
    StringBuilder resultStringBuilder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        resultStringBuilder.append(line).append(NEXT_LINE);
      }
    }
    return resultStringBuilder.toString();
  }

  @PostMapping
  public ResponseEntity<Boolean> getStatusFor(@RequestBody @Valid FeatureFlagRequestDTO featureFlagRequest)
      throws IOException {
    InputStream inputStream = this.getClass()
        .getResourceAsStream(FILE_SEPARATOR + featureFlagRequest.getName() + JSON_EXTENSION);
    return ResponseEntity.status(HttpStatus.OK).body(
        OBJECT_MAPPER.readValue(readFromInputStream(inputStream), FeatureFlag.class).isEnabled(featureFlagRequest));
  }

  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(IllegalArgumentException.class)
  public String handleIllegalArgumentExceptions(IllegalArgumentException ex) {
    return ex.getMessage();
  }

}