package it.fabrick.api.accountmanager.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

/**
 *
 * @author HSelato
 */
public class ServiceErrorHandler implements ResponseErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return (httpResponse.getStatusCode().series() == CLIENT_ERROR || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {

            String errorResponse = new BufferedReader(new InputStreamReader(httpResponse.getBody())).lines().parallel().collect(Collectors.joining("\n"));

            LOGGER.info("Server Error Message ---> " + errorResponse);

            Map<String, String> errorResponseMap = new ObjectMapper().readValue(errorResponse, Map.class);
            throw new AccountManagerException(httpResponse.getStatusCode(), errorResponseMap.get("status"), errorResponseMap.get("errors"), errorResponseMap.get("payload"));
        } else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            LOGGER.info("Client Error Called " + httpResponse.getBody());

            String errorResponse = new BufferedReader(new InputStreamReader(httpResponse.getBody())).lines().parallel().collect(Collectors.joining("\n"));

            LOGGER.info("the encoded error body stream ---> " + errorResponse);
            Map<String, String> errorResponseMap = new ObjectMapper().readValue(errorResponse, Map.class);
            throw new AccountManagerException(httpResponse.getStatusCode(), errorResponseMap.get("status"), errorResponseMap.get("errors"), errorResponseMap.get("payload"));
        }
    }
}
