package it.fabrick.api.accountmanager.exceptions;

import it.fabrick.api.accountmanager.models.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 *
 * @author HSelato
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ApiResponseCodeMapping {
    API_SAVE_API_INFO(HttpStatus.OK, "OK", "{}", "Api Information saved successfully with operation name: "),
    API_GET_API_RESPONSE(HttpStatus.OK, "OK", "{}", ""),
    API_UPDATE_API_RESPONSE(HttpStatus.OK, "OK", "{}", ""),
    API_DELETE_API_RESPONSE(HttpStatus.OK, "OK", "{}", "resource successfully removed!"),
    API_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "KO", new ErrorResponse("ACCMGR-503", "Service Unavailable"), "{}"),
    API_BAD_REQUEST_INVALID_OPERATION_NAME(HttpStatus.BAD_REQUEST, "KO", new ErrorResponse("ACCMGR-400", "Invalid Operation name "), "{}"),
    API_MONGO_DB_DOWN(HttpStatus.SERVICE_UNAVAILABLE, "KO", new ErrorResponse("ACCMGR-503", "MongoDb Service Unabailable, Database is down "), "{}");
    ;

    private HttpStatus httpStatus;
    private String status;
    private Object errors;
    private Object payload;

    private ApiResponseCodeMapping(HttpStatus httpStatus, String status, Object error) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.errors = error;
    }
}
