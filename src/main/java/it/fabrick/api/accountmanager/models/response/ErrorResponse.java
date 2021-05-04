package it.fabrick.api.accountmanager.models.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@Data
public class ErrorResponse implements Serializable {
    
    private String code;
    private String description;
}
