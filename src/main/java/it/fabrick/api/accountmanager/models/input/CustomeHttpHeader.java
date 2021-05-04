package it.fabrick.api.accountmanager.models.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author HSelato
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomeHttpHeader implements Serializable {

    @JsonProperty(required = true)
    private String headerName;
    @JsonProperty(required = true)
    private String headerValue;
    @JsonProperty
    private boolean mandatory;
}
