package it.fabrick.api.accountmanager.Controllers;

import it.fabrick.api.accountmanager.exceptions.AccountManagerException;
import it.fabrick.api.accountmanager.exceptions.ApiResponseCodeMapping;
import it.fabrick.api.accountmanager.models.input.ApiInformation;
import it.fabrick.api.accountmanager.models.response.AccountManagerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import it.fabrick.api.accountmanager.services.ApiInfoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author HSelato
 */
@RestController
@RequestMapping(value = "/api/fabrick/info/v1.0")
public class ApiInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiInfoController.class);

    @Autowired
    private ApiInfoService apiInfoService;

    @PostMapping("/add")
    public ResponseEntity<AccountManagerResponse> addApiInfo(@RequestBody ApiInformation apiInfo) {
        LOGGER.info("add apiInfo ");
        try {

            ApiInformation savedApiInfo = apiInfoService.addApiInfo(apiInfo);
            final ApiResponseCodeMapping apiCodeMapping = ApiResponseCodeMapping.API_SAVE_API_INFO;
            AccountManagerResponse accountManagerResponse = new AccountManagerResponse();

            accountManagerResponse.setStatus(apiCodeMapping.getStatus());
            accountManagerResponse.setError(apiCodeMapping.getErrors());
            accountManagerResponse.setPayload(apiCodeMapping.getPayload().toString() + savedApiInfo.getOperationName());
            return ResponseEntity.ok().body(accountManagerResponse);
        } catch (AccountManagerException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(
                    new AccountManagerResponse(ex.getStatus(), ex.getErrors(), ex.getPayload())
            );
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<AccountManagerResponse> featchApiInfo() {
        LOGGER.info("<ApiInfoController> - featch all apiInfo");
        try {
            List<ApiInformation> apiInfoList = apiInfoService.featchAllApiInfo();

            final ApiResponseCodeMapping apiCodeMapping = ApiResponseCodeMapping.API_GET_API_RESPONSE;
            AccountManagerResponse accountManagerResponse = new AccountManagerResponse();
            accountManagerResponse.setStatus(apiCodeMapping.getStatus());
            accountManagerResponse.setError(apiCodeMapping.getErrors());
            accountManagerResponse.setPayload(apiInfoList);

            return ResponseEntity.ok().body(accountManagerResponse);
        } catch (AccountManagerException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(
                    new AccountManagerResponse(ex.getStatus(), ex.getErrors(), ex.getPayload())
            );
        }
    }

    @GetMapping("/get/{operationName}")
    public ResponseEntity<AccountManagerResponse> getApiInfoByOperationName(@PathVariable(value = "operationName") String operationName) {
        LOGGER.info("<ApiInfoController> - get apiInfo by operation name >> " + operationName);

        try {
            ApiInformation apiInfo = apiInfoService.featchApiInfoByOperationName(operationName);

            final ApiResponseCodeMapping apiCodeMapping = ApiResponseCodeMapping.API_GET_API_RESPONSE;
            AccountManagerResponse accountManagerResponse = new AccountManagerResponse();
            accountManagerResponse.setStatus(apiCodeMapping.getStatus());
            accountManagerResponse.setError(apiCodeMapping.getErrors());
            accountManagerResponse.setPayload(apiInfo);

            return ResponseEntity.ok().body(accountManagerResponse);
        } catch (AccountManagerException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(
                    new AccountManagerResponse(ex.getStatus(), ex.getErrors(), ex.getPayload())
            );
        }
    }

    @PutMapping("/update/{operationName}")
    public ResponseEntity<AccountManagerResponse> updateApiInfo(@RequestBody ApiInformation newApiInfo, @PathVariable(value = "operationName") String operationName) {
        LOGGER.info("<ApiInfoController> - update apiInfo by operation name: " + operationName);

        try {
            ApiInformation updatedApiInfo = apiInfoService.updateApiInfo(newApiInfo, operationName);

            final ApiResponseCodeMapping apiCodeMapping = ApiResponseCodeMapping.API_UPDATE_API_RESPONSE;
            AccountManagerResponse accountManagerResponse = new AccountManagerResponse();
            accountManagerResponse.setStatus(apiCodeMapping.getStatus());
            accountManagerResponse.setError(apiCodeMapping.getErrors());
            accountManagerResponse.setPayload(updatedApiInfo);

            return ResponseEntity.ok().body(accountManagerResponse);
        } catch (AccountManagerException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(
                    new AccountManagerResponse(ex.getStatus(), ex.getErrors(), ex.getPayload())
            );
        }
    }

    @DeleteMapping("/delete/{operationName}")
    public ResponseEntity<AccountManagerResponse> removeApiInfo(@PathVariable(value = "operationName") String operationName) {
        LOGGER.info("<ApiInfoController> - delete apiInfo by operation name: " + operationName);

        try {
            apiInfoService.removeApiInfo(operationName);

            final ApiResponseCodeMapping apiCodeMapping = ApiResponseCodeMapping.API_DELETE_API_RESPONSE;
            AccountManagerResponse accountManagerResponse = new AccountManagerResponse();
            accountManagerResponse.setStatus(apiCodeMapping.getStatus());
            accountManagerResponse.setError(apiCodeMapping.getErrors());
            accountManagerResponse.setPayload(apiCodeMapping.getPayload());

            return ResponseEntity.ok().body(accountManagerResponse);
        } catch (AccountManagerException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(
                    new AccountManagerResponse(ex.getStatus(), ex.getErrors(), ex.getPayload())
            );
        }
    }
}
