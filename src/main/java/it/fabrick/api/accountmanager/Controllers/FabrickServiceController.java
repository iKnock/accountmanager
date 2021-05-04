package it.fabrick.api.accountmanager.Controllers;

import it.fabrick.api.accountmanager.exceptions.AccountManagerException;
import it.fabrick.api.accountmanager.input.MoneyTransfer;
import it.fabrick.api.accountmanager.models.response.AccountManagerResponse;
import it.fabrick.api.accountmanager.services.ApiLoggerService;
import it.fabrick.api.accountmanager.services.FabrickServiceClient;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HSelato
 */
@RestController
@RequestMapping(value = "/api/fabrick/account/v1.0")
public class FabrickServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FabrickServiceController.class);

    @Autowired
    private FabrickServiceClient accountService;

    @Autowired
    private ApiLoggerService apiLoggerService;

    @GetMapping(value = "/balance/{accountId}")
    public ResponseEntity<AccountManagerResponse> getAccountBalance(
            @PathVariable(value = "accountId") String accountId,
            @RequestParam(name = "operationName") String operationName,
            @RequestHeader(value = "User-Agent") String userAgent,
            HttpServletRequest request) {

        LOGGER.info("<AccountServiceController> - getAccountBalance");
        ResponseEntity<AccountManagerResponse> accountBalance;
        try {
            accountBalance = accountService.featchAccountBalance(accountId, operationName);
            apiLoggerService.logApiRequest(request, operationName, "OK", userAgent, accountId);
            return accountBalance;
        } catch (AccountManagerException ex) {
            LOGGER.error("Exception: " + ex);
            apiLoggerService.logApiRequest(request, operationName, "KO", userAgent, accountId);
            return ResponseEntity.status(ex.getHttpStatus()).body(new AccountManagerResponse(ex.getStatus(), ex.getErrors(), ex.getPayload()));
        }
    }

    @GetMapping(value = "/transaction/{accountId}")
    public ResponseEntity<AccountManagerResponse> getTransactionList(
            @PathVariable(value = "accountId") String accountId,
            @RequestParam(name = "operationName") String operationName,
            @RequestParam(name = "fromAccountingDate") String fromAccountingDate,
            @RequestParam(name = "toAccountingDate") String toAccountingDate,
            @RequestHeader(value = "User-Agent") String userAgent,
            HttpServletRequest request) {

        LOGGER.info("<AccountServiceController> - getTransactionList");
        ResponseEntity<AccountManagerResponse> transactionList;
        try {
            transactionList = accountService.featchTransactionList(accountId, operationName, fromAccountingDate, toAccountingDate);
            apiLoggerService.logApiRequest(request, operationName, "OK", userAgent, accountId);
            LOGGER.info("<AccountServiceController> - the payload= " + transactionList.getBody());
            return transactionList;
        } catch (AccountManagerException ex) {
            LOGGER.error("Exception: " + ex);
            apiLoggerService.logApiRequest(request, operationName, "KO", userAgent, accountId);
            return ResponseEntity.status(ex.getHttpStatus()).body(new AccountManagerResponse(ex.getStatus(), ex.getErrors(), ex.getPayload())
            );
        }
    }

    @PostMapping(value = "/transfer/{accountId}")
    public ResponseEntity<AccountManagerResponse> performMoneyTransfer(
            @PathVariable(value = "accountId") String accountId,
            @RequestParam(name = "operationName") String operationName,
            @RequestBody MoneyTransfer moneyTransfer,
            @RequestHeader(value = "User-Agent") String userAgent,
            HttpServletRequest request) {

        LOGGER.info("<AccountServiceController> - performMoneyTransfer");
        ResponseEntity<AccountManagerResponse> accountBalance;
        try {
            accountBalance = accountService.handleMoneyTransfer(accountId, operationName, moneyTransfer);
            apiLoggerService.logMoneyTransferRequest(request, operationName, "OK", userAgent, moneyTransfer, accountId);
            return accountBalance;
        } catch (AccountManagerException ex) {
            LOGGER.error("Exception: " + ex);
            apiLoggerService.logMoneyTransferRequest(request, operationName, "KO", userAgent, moneyTransfer, accountId);
            return ResponseEntity.status(ex.getHttpStatus()).body(new AccountManagerResponse(ex.getStatus(), ex.getErrors(), ex.getPayload()));
        }
    }
}
