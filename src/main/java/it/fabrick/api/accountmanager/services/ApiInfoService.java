package it.fabrick.api.accountmanager.services;

import it.fabrick.api.accountmanager.exceptions.AccountManagerException;
import it.fabrick.api.accountmanager.exceptions.ApiResponseCodeMapping;
import it.fabrick.api.accountmanager.models.input.ApiInformation;
import it.fabrick.api.accountmanager.models.response.AccountManagerResponse;
import it.fabrick.api.accountmanager.repository.ApiInfoRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author HSelato
 */
@Service
public class ApiInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiInfoService.class);

    @Autowired
    private ApiInfoRepository apiInfoDao;

    @Cacheable(value = "apiInfo", key = "#apiInfo.operationName", unless = "#result == null")
    public ApiInformation addApiInfo(ApiInformation apiInfo) throws AccountManagerException {
        LOGGER.info("<ApiInfoService> - add apiInfo ");
        ApiInformation savedApiInfo = apiInfoDao.insert(apiInfo);

        if (savedApiInfo == null) {
            final ApiResponseCodeMapping errorCodeMapping = ApiResponseCodeMapping.API_SERVICE_UNAVAILABLE;
            throw new AccountManagerException(errorCodeMapping.getHttpStatus(), errorCodeMapping.getStatus(), errorCodeMapping.getErrors(), errorCodeMapping.getPayload());
        }
        return savedApiInfo;
    }

    public List<ApiInformation> featchAllApiInfo() throws AccountManagerException {
        LOGGER.info("<ApiInfoService> - featch all apiInfo");
        List<ApiInformation> apiInfoList = apiInfoDao.findAll();

        return apiInfoList;
    }

    @Cacheable(value = "apiInfo", key = "#operationName", unless = "#result == null")
    public ApiInformation featchApiInfoByOperationName(String operationName) throws AccountManagerException {
        LOGGER.info("<ApiInfoService> - fetch apiInfo by operation name: " + operationName);
        ApiInformation apiInfo = apiInfoDao.findByOperationName(operationName);

        if (apiInfo == null) {
            LOGGER.error("<ApiInfoService> - apiInfo for - " + operationName + " - is null: ");
            final ApiResponseCodeMapping errorCodeMapping = ApiResponseCodeMapping.API_BAD_REQUEST_INVALID_OPERATION_NAME;
            throw new AccountManagerException(errorCodeMapping.getHttpStatus(), errorCodeMapping.getStatus(), errorCodeMapping.getErrors(), errorCodeMapping.getPayload());
        }
        return apiInfo;
    }

    @CachePut(value = "apiInfo", key = "#operationName")
    public ApiInformation updateApiInfo(@RequestBody ApiInformation newApiInfo, @PathVariable(value = "operationName") String operationName) throws AccountManagerException {
        LOGGER.info("<ApiInfoService> - update apiInfo by operation name: " + operationName);

        ApiInformation apiInfo = featchApiInfoByOperationName(operationName);

        apiInfo.setDomain(newApiInfo.getDomain());
        apiInfo.setEndpoint(newApiInfo.getEndpoint());
        apiInfo.setMethod(newApiInfo.getMethod());
        apiInfo.setUriParam(newApiInfo.getUriParam());
        apiInfo.setUriQuery(newApiInfo.getUriQuery());
        apiInfo.setHttpHeader(newApiInfo.getHttpHeader());
        apiInfo = apiInfoDao.save(apiInfo);

        return apiInfo;
    }

    @CacheEvict(value = "apiInfo", key = "#operationName")
    public ApiInformation removeApiInfo(@PathVariable(value = "operationName") String operationName) throws AccountManagerException {
        LOGGER.info("<ApiInfoService> - delete apiInfo by operation name: " + operationName);

        ApiInformation apiInfo = featchApiInfoByOperationName(operationName);
        apiInfoDao.delete(apiInfo);       
        return apiInfo;
    }

}
