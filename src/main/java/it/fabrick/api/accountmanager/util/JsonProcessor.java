package it.fabrick.api.accountmanager.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.fabrick.api.accountmanager.exceptions.AccountManagerException;
import it.fabrick.api.accountmanager.models.response.ErrorResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 *
 * @author HSelato
 */
@Component
public class JsonProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonProcessor.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);        
    }

    public static <T extends Object> T fromJson(final String json, final Class<T> clz) throws AccountManagerException {
        LOGGER.info("from json to object ");
        T c = null;
        try {
            c = OBJECT_MAPPER.readValue(json, clz);
        } catch (final IOException e) {
            LOGGER.error("Error converting json to obj " + e.getMessage());
            throw new AccountManagerException(HttpStatus.BAD_REQUEST, "KO", new ErrorResponse("ERR-001", e.getMessage()), "{}");
        }
        return c;
    }

    public static String toJson(final Object claz) {
        LOGGER.info("from object to json ");
        String json = "";
        try {
            json = OBJECT_MAPPER.writer().withDefaultPrettyPrinter().writeValueAsString(claz);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error converting class to json " + ex.getMessage());
            throw new AccountManagerException(ex.getMessage());
        }
        return json;
    }
}
