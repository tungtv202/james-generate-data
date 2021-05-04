package me.tungexplorer.james_generate_data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JmapSessionResponse {
    @JsonProperty("primaryAccounts")
    private PrimaryAccounts account;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrimaryAccounts {
        @JsonProperty("com:linagora:params:jmap:pgp")
        private String pgpAccountId;
    }
}
