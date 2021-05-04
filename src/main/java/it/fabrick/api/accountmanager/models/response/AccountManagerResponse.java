package it.fabrick.api.accountmanager.models.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class AccountManagerResponse implements Serializable {

    private String status;
    private Object error;
    private Object payload;

}
