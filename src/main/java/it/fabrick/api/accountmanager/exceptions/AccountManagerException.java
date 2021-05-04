package it.fabrick.api.accountmanager.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author HSelato
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountManagerException extends RuntimeException {

    private HttpStatus httpStatus;
    private String status;
    private Object errors;
    private Object payload;

    public AccountManagerException(final String messages) {
        super(messages);
    }

}
