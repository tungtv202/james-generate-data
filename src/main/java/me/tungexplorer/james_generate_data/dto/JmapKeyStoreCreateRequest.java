package me.tungexplorer.james_generate_data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class JmapKeyStoreCreateRequest extends JmapBaseRequest {
    private List<List<Evo>> methodCalls = new ArrayList<>();


    public JmapKeyStoreCreateRequest(String accountId, String pgpPubKey) {
        this.using.add("urn:ietf:params:jmap:core");
        this.using.add("com:linagora:params:jmap:pgp");

        List<Evo> evos = List.of(new Evo(accountId, pgpPubKey));
        methodCalls.add(evos);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Evo {
        @JsonProperty("Keystore/set")
        private CreateInfo evoKey;

        public Evo(String accountId, String pgp) {
            evoKey = new CreateInfo(accountId, pgp);
        }
    }

    @Data
    @NoArgsConstructor
    public static class CreateInfo {
        private String accountId;
        private HashMap<String, Key> create = new HashMap<>();

        public CreateInfo(String accountId, String pgp) {
            this.accountId = accountId;
            this.create.put("clientKey888", new Key(pgp));
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        private static class Key {
            private String key;
        }
    }
}

