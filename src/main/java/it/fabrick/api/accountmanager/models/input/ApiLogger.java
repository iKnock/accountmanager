package it.fabrick.api.accountmanager.models.input;

import it.fabrick.api.accountmanager.input.MoneyTransfer;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author HSelato
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "api_logger")
public class ApiLogger implements Serializable {

    @Id
    private String id;
    private String endpoint;
    private String operation;
    private String method;
    private List<String> uriParam;
    private String queryString;
    private String ip;
    private String host;
    private String userAgent;
    private String timestamp;
    private String status;
    private MoneyTransfer moneyTransferInput;

}
