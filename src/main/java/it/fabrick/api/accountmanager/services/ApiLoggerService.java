package it.fabrick.api.accountmanager.services;

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

    public void logApiRequest(final HttpServletRequest request, final String operation, final String status, final String userAgent, final String... apiInputs) {

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

        apiLoggerDao.insert(apiLogger);

    }

    public void logMoneyTransferRequest(final HttpServletRequest request, final String operation, final String status, final String userAgent, final MoneyTransfer transfer, final String... apiInputs) {
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

        apiLoggerDao.insert(apiLogger);
    }

}
