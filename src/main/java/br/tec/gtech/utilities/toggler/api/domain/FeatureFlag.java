package br.tec.gtech.utilities.toggler.api.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureFlag implements Serializable {
    private String name;
    private Type type;
    public boolean value;
    private Condition condition;

    public boolean isEnabled(FeatureFlagRequestDTO featureFlagRequest) {
        assertValid(featureFlagRequest);
        
        if (type.equals(Type.RELEASE)) {
            return this.value;
        }

        return Operation.valueOfLabel(this.condition.getOperation().toUpperCase()).isEnabled(featureFlagRequest.getArgument().getValue(), condition.getValue());   
    }

    private void assertValid(FeatureFlagRequestDTO featureFlagRequest){
        if(type.equals(Type.CONTEXT) && featureFlagRequest.getArgument() == null){
            throw new IllegalArgumentException("FeatureFlag type 'Context' requires argument");
        }
    }

}