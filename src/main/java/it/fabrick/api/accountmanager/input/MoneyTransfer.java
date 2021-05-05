package it.fabrick.api.accountmanager.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author HSelato
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@EqualsAndHashCode
public class MoneyTransfer implements Serializable {

    private String accountId;
    private Creditor creditor;

    private String executionDate;//optional    
    private String description;
    private String amount;
    private String currency;

    private TaxRelief taxRelief;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Data
    @EqualsAndHashCode
    class Creditor implements Serializable {

        private String name;
        private Account account;
        private Address address;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        @Data
        @EqualsAndHashCode
        class Account implements Serializable {

            private String accountCode;
            private String bicCode;
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        @Data
        @EqualsAndHashCode
        class Address implements Serializable {

            private String address;
            private String city;
            private String countryCode;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Data
    @EqualsAndHashCode
    class TaxRelief implements Serializable {

        @JsonProperty
        private boolean isCondoUpgrade;
        private String creditorFiscalCode;
        private String beneficiaryType;

        private NaturalPersonBeneficiary naturalPersonBeneficiary;
        private LegalPersonBeneficiary legalPersonBeneficiary;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        @Data
        @EqualsAndHashCode
        class NaturalPersonBeneficiary implements Serializable {

            private String fiscalCode1;
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        @Data
        @EqualsAndHashCode
        class LegalPersonBeneficiary implements Serializable {

            private String fiscalCode;
        }
    }

}
