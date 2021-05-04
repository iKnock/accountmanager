package it.fabrick.api.accountmanager.models.input;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
@Document(collection = "api_information")
public class ApiInformation implements Serializable {

    @Id
    private String id;
    @Indexed(unique = true)
    private String operationName;
    private String domain;
    private String endpoint;
    private String method;
    private String[] uriParam;
    private String[] uriQuery;
    private List<CustomeHttpHeader> httpHeader;

}
