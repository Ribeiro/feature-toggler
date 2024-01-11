package br.tec.gtech.utilities.toggler.api.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Argument implements Serializable{
    private String value;
}