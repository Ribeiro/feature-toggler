package br.tec.gtech.utilities.toggler.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class Condition implements Serializable{
    private String field;
    private Object value;
    private String operation;
}