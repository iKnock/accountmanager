package it.fabrick.api.accountmanager.controller;

import it.fabrick.api.accountmanager.input.MoneyTransfer;
import it.fabrick.api.accountmanager.models.response.AccountManagerResponse;
import it.fabrick.api.accountmanager.util.JsonProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

/**
 *
 * @author HSelato
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class AccountManagerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountManagerTest.class);

    final private static String BASE_URL = "http://localhost:";
    final private static int PORT = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    private String accountId = "14537780";
    private String operationName = "account-balance";
    private final String userAgent = "Testing-PostmanRuntime/7.26.8-Testing";

    private String fromAccountingDate = "2019-01-01";
    private String toAccountingDate = "2019-04-01";

    @Test
    public void testGetAccountBalanceSuccess() {
        LOGGER.info("testGetAccountBalanceSuccess: ---> ");

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);

        String uri = BASE_URL + PORT + "/api/fabrick/account/v1.0/balance/{accountId}";
        uri = uri + "?" + "operationName" + "=" + operationName;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", userAgent);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<AccountManagerResponse> accountMangerResponseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                AccountManagerResponse.class,
                uriParams);

        String responseJson = JsonProcessor.toJson(accountMangerResponseEntity.getBody());
        LOGGER.info("jsonNode: ---> " + responseJson);

        AccountManagerResponse accoutManagerResponse = JsonProcessor.fromJson(responseJson, AccountManagerResponse.class);
        LOGGER.info("accoutManagerResponse: ---> " + accoutManagerResponse.getPayload());

        assertEquals(HttpStatus.OK, accountMangerResponseEntity.getStatusCode());
        assertTrue(accoutManagerResponse.getPayload().toString().contains("availableBalance"));
    }

    @Test
    public void testGetAccountBalanceFailByWrongOperationName() {
        LOGGER.info("testGetAccountBalanceFailByWrongOperationName: ---> ");

        operationName = "wrong-value";
        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);

        String uri = BASE_URL + PORT + "/api/fabrick/account/v1.0/balance/{accountId}";
        uri = uri + "?" + "operationName" + "=" + operationName;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", userAgent);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        LOGGER.info("uri to test: ---> " + uri);

        ResponseEntity<AccountManagerResponse> accountMangerResponseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                AccountManagerResponse.class,
                uriParams);

        String responseJson = JsonProcessor.toJson(accountMangerResponseEntity.getBody());
        LOGGER.info("jsonNode: ---> " + responseJson);

        AccountManagerResponse accoutManagerResponse = JsonProcessor.fromJson(responseJson, AccountManagerResponse.class);
        LOGGER.info("accoutManagerResponse: ---> " + accoutManagerResponse.getPayload());

        assertEquals(HttpStatus.BAD_REQUEST, accountMangerResponseEntity.getStatusCode());
        assertTrue(accoutManagerResponse.getError().toString().contains("Invalid Operation name"));
    }

    @Test
    public void testGetAccountBalanceFailByWrongAccountId() {
        LOGGER.info("testGetAccountBalanceFailByWrongAccountId: ---> ");

        accountId = "wrong-value";
        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);

        String uri = BASE_URL + PORT + "/api/fabrick/account/v1.0/balance/{accountId}";
        uri = uri + "?" + "operationName" + "=" + operationName;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", userAgent);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<AccountManagerResponse> accountMangerResponseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                AccountManagerResponse.class,
                uriParams);

        String responseJson = JsonProcessor.toJson(accountMangerResponseEntity.getBody());
        LOGGER.info("jsonNode: ---> " + responseJson);

        AccountManagerResponse accoutManagerResponse = JsonProcessor.fromJson(responseJson, AccountManagerResponse.class);
        LOGGER.info("accoutManagerResponse: ---> " + accoutManagerResponse.getPayload());

        assertEquals(HttpStatus.BAD_REQUEST, accountMangerResponseEntity.getStatusCode());
        assertTrue(accoutManagerResponse.getError().toString().contains("Invalid account identifier"));
    }

    @Test
    public void testGetAccountBalanceFailIpNotInRange() {
        LOGGER.info("testGetAccountBalanceFailIpNotInRange: ---> ");

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);

        String uri = BASE_URL + PORT + "/api/fabrick/account/v1.0/balance/{accountId}";
        uri = uri + "?" + "operationName" + "=" + operationName;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", userAgent);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<AccountManagerResponse> accountMangerResponseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                AccountManagerResponse.class,
                uriParams);

        String responseJson = JsonProcessor.toJson(accountMangerResponseEntity.getBody());
        LOGGER.info("jsonNode: ---> " + responseJson);

        AccountManagerResponse accoutManagerResponse = JsonProcessor.fromJson(responseJson, AccountManagerResponse.class);
        LOGGER.info("accoutManagerResponse: ---> " + accoutManagerResponse.getPayload());

        assertEquals(HttpStatus.FORBIDDEN, accountMangerResponseEntity.getStatusCode());
        assertTrue(accoutManagerResponse.getError().toString().contains("Ip not in range"));
    }

    //Transaction List
    @Test
    public void testGetTransactionListSuccess() {
        LOGGER.info("testGetTransactionListSuccess: ---> ");

        operationName = "list-transactions";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);

        String uri = BASE_URL + PORT + "/api/fabrick/account/v1.0/transaction/{accountId}";
        uri = uri + "?" + "operationName" + "=" + operationName + "&" + "fromAccountingDate" + "=" + fromAccountingDate + "&" + "toAccountingDate" + "=" + toAccountingDate;

        LOGGER.info("uri: ---> " + uri);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", userAgent);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<AccountManagerResponse> accountMangerResponseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                AccountManagerResponse.class,
                uriParams);

        String responseJson = JsonProcessor.toJson(accountMangerResponseEntity.getBody());
        LOGGER.info("responseJson: ---> " + responseJson);

        AccountManagerResponse accoutManagerResponse = JsonProcessor.fromJson(responseJson, AccountManagerResponse.class);
        LOGGER.info("accoutManagerResponse: ---> " + accoutManagerResponse.getPayload());

        assertEquals(HttpStatus.OK, accountMangerResponseEntity.getStatusCode());
        assertTrue(accoutManagerResponse.getPayload().toString().contains("amount"));
    }

    @Test
    public void testGetTransactionListFailByWrongOperationName() {
        LOGGER.info("testGetTransactionListFailByWrongOperationName: ---> ");

        operationName = "list-wrong";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);

        String uri = BASE_URL + PORT + "/api/fabrick/account/v1.0/transaction/{accountId}";
        uri = uri + "?" + "operationName" + "=" + operationName + "&" + "fromAccountingDate" + "=" + fromAccountingDate + "&" + "toAccountingDate" + "=" + toAccountingDate;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", userAgent);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        LOGGER.info("uri to test: ---> " + uri);

        ResponseEntity<AccountManagerResponse> accountMangerResponseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                AccountManagerResponse.class,
                uriParams);

        String responseJson = JsonProcessor.toJson(accountMangerResponseEntity.getBody());
        LOGGER.info("jsonNode: ---> " + responseJson);

        AccountManagerResponse accoutManagerResponse = JsonProcessor.fromJson(responseJson, AccountManagerResponse.class);
        LOGGER.info("accoutManagerResponse: ---> " + accoutManagerResponse.getPayload());

        assertEquals(HttpStatus.BAD_REQUEST, accountMangerResponseEntity.getStatusCode());
        assertTrue(accoutManagerResponse.getError().toString().contains("Invalid Operation name"));
    }

    @Test
    public void testGetTransactionListFailByWrongAccountId() {
        LOGGER.info("testGetTransactionListFailByWrongAccountId: ---> ");

        operationName = "list-transactions";
        accountId = "wrong-value";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);

        String uri = BASE_URL + PORT + "/api/fabrick/account/v1.0/transaction/{accountId}";
        uri = uri + "?" + "operationName" + "=" + operationName + "&" + "fromAccountingDate" + "=" + fromAccountingDate + "&" + "toAccountingDate" + "=" + toAccountingDate;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", userAgent);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<AccountManagerResponse> accountMangerResponseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                AccountManagerResponse.class,
                uriParams);

        String responseJson = JsonProcessor.toJson(accountMangerResponseEntity.getBody());
        LOGGER.info("jsonNode: ---> " + responseJson);

        AccountManagerResponse accoutManagerResponse = JsonProcessor.fromJson(responseJson, AccountManagerResponse.class);
        LOGGER.info("accoutManagerResponse: ---> " + accoutManagerResponse.getPayload());

        assertEquals(HttpStatus.BAD_REQUEST, accountMangerResponseEntity.getStatusCode());
        assertTrue(accoutManagerResponse.getError().toString().contains("Invalid account identifier"));
    }

    @Test
    public void testGetTransactionListFailIpNotInRange() {
        LOGGER.info("testGetTransactionListFailIpNotInRange: ---> ");

        operationName = "list-transactions";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);

        String uri = BASE_URL + PORT + "/api/fabrick/account/v1.0/transaction/{accountId}";
        uri = uri + "?" + "operationName" + "=" + operationName + "&" + "fromAccountingDate" + "=" + fromAccountingDate + "&" + "toAccountingDate" + "=" + toAccountingDate;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", userAgent);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<AccountManagerResponse> accountMangerResponseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                AccountManagerResponse.class,
                uriParams);

        String responseJson = JsonProcessor.toJson(accountMangerResponseEntity.getBody());
        LOGGER.info("jsonNode: ---> " + responseJson);

        AccountManagerResponse accoutManagerResponse = JsonProcessor.fromJson(responseJson, AccountManagerResponse.class);
        LOGGER.info("accoutManagerResponse: ---> " + accoutManagerResponse.getPayload());

        assertEquals(HttpStatus.FORBIDDEN, accountMangerResponseEntity.getStatusCode());
        assertTrue(accoutManagerResponse.getError().toString().contains("Ip not in range"));
    }

    //transfer
    @Test
    public void testPostMoneyTransferFailByWrongOperationName() {
        LOGGER.info("testPostMoneyTransferFailByWrongOperationName: ---> ");

        final InputStream fileInputStream = AccountManagerTest.class.getResourceAsStream("/static/transferJsonInput.json");
        final String transferJsonBody;
        MoneyTransfer transferObj = null;
        try {
            transferJsonBody = StreamUtils.copyToString(fileInputStream, Charset.defaultCharset());
            transferObj = JsonProcessor.fromJson(transferJsonBody, MoneyTransfer.class);
        } catch (IOException ex) {
            LOGGER.info("IOException: ---> " + ex);
        }

        operationName = "transfer-wrong";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);

        String uri = BASE_URL + PORT + "/api/fabrick/account/v1.0/transfer/{accountId}";
        uri = uri + "?" + "operationName" + "=" + operationName;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", userAgent);

        HttpEntity<MoneyTransfer> entity = new HttpEntity<>(transferObj, headers);

        LOGGER.info("uri to test: ---> " + uri);

        ResponseEntity<AccountManagerResponse> accountMangerResponseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                AccountManagerResponse.class,
                uriParams);

        String responseJson = JsonProcessor.toJson(accountMangerResponseEntity.getBody());
        LOGGER.info("jsonNode: ---> " + responseJson);

        AccountManagerResponse accoutManagerResponse = JsonProcessor.fromJson(responseJson, AccountManagerResponse.class);
        LOGGER.info("accoutManagerResponse: ---> " + accoutManagerResponse.getPayload());

        assertEquals(HttpStatus.BAD_REQUEST, accountMangerResponseEntity.getStatusCode());
        assertTrue(accoutManagerResponse.getError().toString().contains("Invalid Operation name"));
    }

    @Test
    public void testPostMoneyTransferFailByWrongAccountId() {
        LOGGER.info("testPostMoneyTransferFailByWrongAccountId: ---> ");

        final InputStream fileInputStream = AccountManagerTest.class.getResourceAsStream("/static/transferJsonInput.json");
        final String transferJsonBody;
        MoneyTransfer transferObj = null;
        try {
            transferJsonBody = StreamUtils.copyToString(fileInputStream, Charset.defaultCharset());
            transferObj = JsonProcessor.fromJson(transferJsonBody, MoneyTransfer.class);
        } catch (IOException ex) {
            LOGGER.info("IOException: ---> " + ex);
        }

        operationName = "transfer";
        accountId = "wrong-value";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);

        String uri = BASE_URL + PORT + "/api/fabrick/account/v1.0/transfer/{accountId}";
        uri = uri + "?" + "operationName" + "=" + operationName;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", userAgent);
        HttpEntity<MoneyTransfer> entity = new HttpEntity<>(transferObj, headers);

        ResponseEntity<AccountManagerResponse> accountMangerResponseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                AccountManagerResponse.class,
                uriParams);

        String responseJson = JsonProcessor.toJson(accountMangerResponseEntity.getBody());
        LOGGER.info("responseJson: ---> " + responseJson);

        AccountManagerResponse accoutManagerResponse = JsonProcessor.fromJson(responseJson, AccountManagerResponse.class);
        LOGGER.info("accoutManagerResponse: ---> " + accoutManagerResponse.getPayload());

        assertEquals(HttpStatus.BAD_REQUEST, accountMangerResponseEntity.getStatusCode());
        assertTrue(accoutManagerResponse.getError().toString().contains("Invalid account identifier"));
    }

    @Test
    public void testPostMoneyTransferFailIpNotInRange() {
        LOGGER.info("testPostMoneyTransferFailIpNotInRange: ---> ");

        final InputStream fileInputStream = AccountManagerTest.class.getResourceAsStream("/static/transferJsonInput.json");
        final String transferJsonBody;
        MoneyTransfer transferObj = null;
        try {
            transferJsonBody = StreamUtils.copyToString(fileInputStream, Charset.defaultCharset());
            transferObj = JsonProcessor.fromJson(transferJsonBody, MoneyTransfer.class);
        } catch (IOException ex) {
            LOGGER.info("IOException: ---> " + ex);
        }

        operationName = "transfer";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("accountId", accountId);

        String uri = BASE_URL + PORT + "/api/fabrick/account/v1.0/transfer/{accountId}";
        uri = uri + "?" + "operationName" + "=" + operationName;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", userAgent);
        HttpEntity<MoneyTransfer> entity = new HttpEntity<>(transferObj, headers);

        ResponseEntity<AccountManagerResponse> accountMangerResponseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                AccountManagerResponse.class,
                uriParams);

        String responseJson = JsonProcessor.toJson(accountMangerResponseEntity.getBody());
        LOGGER.info("jsonNode: ---> " + responseJson);

        AccountManagerResponse accoutManagerResponse = JsonProcessor.fromJson(responseJson, AccountManagerResponse.class);
        LOGGER.info("accoutManagerResponse: ---> " + accoutManagerResponse.getPayload());

        assertEquals(HttpStatus.FORBIDDEN, accountMangerResponseEntity.getStatusCode());
        assertTrue(accoutManagerResponse.getError().toString().contains("Ip not in range"));
    }
}
