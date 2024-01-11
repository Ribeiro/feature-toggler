package br.tec.gtech.utilities.toggler.api.domain;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureFlagRequestDTO implements Serializable {
    @NotBlank(message = "Name is mandatory")
    private String name;
    private Argument argument;
  
}