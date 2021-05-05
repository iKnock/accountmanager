package it.fabrick.api.accountmanager.services;

import it.fabrick.api.accountmanager.exceptions.AccountManagerException;
import it.fabrick.api.accountmanager.exceptions.ServiceErrorHandler;
import it.fabrick.api.accountmanager.models.input.ApiInformation;
import it.fabrick.api.accountmanager.models.input.CustomeHttpHeader;
import it.fabrick.api.accountmanager.input.MoneyTransfer;
import it.fabrick.api.accountmanager.models.response.AccountManagerResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author HSelato
 */
@Service
public class FabrickServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FabrickServiceClient.class);

    @Autowired
    private ApiInfoService apiInfoService;

    private final RestTemplate restTemplate;

    @Autowired
    public FabrickServiceClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(30000))
                .setReadTimeout(Duration.ofMillis(30000))
                .errorHandler(new ServiceErrorHandler())
                .build();
    }

    public ResponseEntity<AccountManagerResponse> featchAccountBalance(final String accountId, final String operationName) throws AccountManagerException {
        LOGGER.info("<FabrickServiceClient> - featchAccountBalance");
        ApiInformation apiInfo = apiInfoService.featchApiInfoByOperationName(operationName);

        final String uri = apiInfo.getDomain() + apiInfo.getEndpoint();
        LOGGER.info("<FabrickServiceClient> - uri: " + uri);

        Map<String, String> uriParams = new HashMap<>();

        if (apiInfo.getUriParam().length > 0) {
            uriParams = populateUriParams(apiInfo.getUriParam(), accountId);
        }

        HttpEntity<String> entity = new HttpEntity<>(populateHttpHeader(apiInfo.getHttpHeader()));

        ResponseEntity<AccountManagerResponse> accountBalance = restTemplate.exchange(uri, HttpMethod.GET, entity, AccountManagerResponse.class, uriParams);
        LOGGER.info("<FabrickServiceClient> - Account Balance ->: " + accountBalance.getBody());

        return accountBalance;
    }

    public ResponseEntity<AccountManagerResponse> featchTransactionList(final String accountId, final String operationName, final String fromDate, final String toDate) throws AccountManagerException {
        LOGGER.info("<FabrickServiceClient> - featchTransactionList");
        ApiInformation apiInfo = apiInfoService.featchApiInfoByOperationName(operationName);

        String uri = apiInfo.getDomain() + apiInfo.getEndpoint();

        Map<String, String> uriParams = new HashMap<>();

        if (apiInfo.getUriParam().length > 0) {
            uriParams = populateUriParams(apiInfo.getUriParam(), accountId);
        }

        final String[] uriQueries = apiInfo.getUriQuery();
        if (uriQueries.length > 0) {
            uri = getUriWithQueryParam(uriQueries, fromDate, toDate, uri);
        }

        HttpEntity<String> entity = new HttpEntity<>(populateHttpHeader(apiInfo.getHttpHeader()));

        ResponseEntity<AccountManagerResponse> transactionList = restTemplate.exchange(uri, HttpMethod.GET, entity, AccountManagerResponse.class, uriParams);
        LOGGER.info("List of transaction ->: " + transactionList.getBody());

        return transactionList;
    }

    public ResponseEntity<AccountManagerResponse> handleMoneyTransfer(final String accountId, final String operationName, final MoneyTransfer moneyTransfer) throws AccountManagerException {
        LOGGER.info("<FabrickServiceClient> - handleMoneyTransfer");
        ApiInformation apiInfo = apiInfoService.featchApiInfoByOperationName(operationName);

        String uri = apiInfo.getDomain() + apiInfo.getEndpoint();

        Map<String, String> uriParams = new HashMap<>();

        if (apiInfo.getUriParam().length > 0) {
            uriParams = populateUriParams(apiInfo.getUriParam(), accountId);
        }

        HttpEntity<MoneyTransfer> entity = new HttpEntity<>(moneyTransfer, populateHttpHeader(apiInfo.getHttpHeader()));

        ResponseEntity<AccountManagerResponse> transactionList = restTemplate.exchange(uri, HttpMethod.POST, entity, AccountManagerResponse.class, uriParams);
        LOGGER.info("Money Transfer -> : " + transactionList.getBody());

        return transactionList;
    }

    private Map<String, String> populateUriParams(final String[] uriParamInput, final String accountId) {
        Map<String, String> uriParams = new HashMap<>();

        for (String uriParam : uriParamInput) {
            uriParams.put(uriParam, accountId);
        }
        return uriParams;
    }

    private String getUriWithQueryParam(String[] uriQueries, final String fromDate, final String toDate, String uri) {       
        uri = uri + "?" + uriQueries[0] + "=" + fromDate + "&" + uriQueries[1] + "=" + toDate;
        LOGGER.info("uri: " + uri);

        return uri;
    }

    private HttpHeaders populateHttpHeader(List<CustomeHttpHeader> customeHttpHeaderList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        customeHttpHeaderList.forEach((customHttpHeader) -> {
            headers.set(customHttpHeader.getHeaderName(), customHttpHeader.getHeaderValue());
        });

        return headers;
    }
}
