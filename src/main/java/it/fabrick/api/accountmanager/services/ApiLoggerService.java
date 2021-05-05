package it.fabrick.api.accountmanager.services;

import it.fabrick.api.accountmanager.exceptions.AccountManagerException;
import it.fabrick.api.accountmanager.exceptions.ApiResponseCodeMapping;
import it.fabrick.api.accountmanager.input.MoneyTransfer;
import it.fabrick.api.accountmanager.models.input.ApiLogger;
import it.fabrick.api.accountmanager.repository.ApiLoggerRepository;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author HSelato
 */
@Service
public class ApiLoggerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiLoggerService.class);

    @Autowired
    private ApiLoggerRepository apiLoggerDao;

    public void logApiRequest(final HttpServletRequest request, final String operation, final String status, final String userAgent, final String... apiInputs) throws AccountManagerException {

        ApiLogger apiLogger = new ApiLogger();
        apiLogger.setEndpoint(request.getRequestURL().toString());
        apiLogger.setMethod(request.getMethod());
        apiLogger.setOperation(operation);
        apiLogger.setUriParam(Arrays.asList(apiInputs));
        apiLogger.setQueryString(request.getQueryString());
        apiLogger.setUserAgent(userAgent);
        apiLogger.setTimestamp(ZonedDateTime.now().toString());
        apiLogger.setStatus(status);
        try {
            apiLogger.setIp(InetAddress.getLocalHost().getHostAddress());//confirm the ip is correct and check also request.getRemoteAddr()
            apiLogger.setHost(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException ex) {
            LOGGER.info("<ApiLoggerService> - ex: " + ex);
        }

        try {
            apiLoggerDao.insert(apiLogger);
        } catch (DataAccessException ex) {
            LOGGER.info("<DataAccessException -----> " + ex.getMessage());
            final ApiResponseCodeMapping errorCodeMapping = ApiResponseCodeMapping.API_MONGO_DB_DOWN;
            throw new AccountManagerException(errorCodeMapping.getHttpStatus(), errorCodeMapping.getStatus(), errorCodeMapping.getErrors(), errorCodeMapping.getPayload());
        }

    }

    public void logMoneyTransferRequest(final HttpServletRequest request, final String operation, final String status, final String userAgent, final MoneyTransfer transfer, final String... apiInputs) throws AccountManagerException {
        ApiLogger apiLogger = new ApiLogger();
        apiLogger.setEndpoint(request.getRequestURL().toString());
        apiLogger.setMethod(request.getMethod());
        apiLogger.setOperation(operation);
        apiLogger.setUriParam(Arrays.asList(apiInputs));
        apiLogger.setQueryString(request.getQueryString());
        apiLogger.setUserAgent(userAgent);

        apiLogger.setTimestamp(ZonedDateTime.now().toString());
        apiLogger.setStatus(status);
        apiLogger.setMoneyTransferInput(transfer);
        try {
            apiLogger.setIp(InetAddress.getLocalHost().getHostAddress());//confirm the ip is correct 
            apiLogger.setHost(InetAddress.getLocalHost().getHostName());//confirm the ip is correct 
        } catch (UnknownHostException ex) {
            LOGGER.info("<ApiLoggerService> - ex: " + ex);
        }

        try {
            apiLoggerDao.insert(apiLogger);
        } catch (DataAccessException ex) {
            LOGGER.info("<DataAccessException -----> " + ex.getMessage());
            final ApiResponseCodeMapping errorCodeMapping = ApiResponseCodeMapping.API_MONGO_DB_DOWN;
            throw new AccountManagerException(errorCodeMapping.getHttpStatus(), errorCodeMapping.getStatus(), errorCodeMapping.getErrors(), errorCodeMapping.getPayload());
        }
    }

}
