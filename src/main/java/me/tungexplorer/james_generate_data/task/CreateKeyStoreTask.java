package me.tungexplorer.james_generate_data.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.tungexplorer.james_generate_data.client.JmapClient;
import me.tungexplorer.james_generate_data.client.UserClient;
import me.tungexplorer.james_generate_data.dto.User;
import me.tungexplorer.james_generate_data.dto.UserList;
import me.tungexplorer.james_generate_data.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateKeyStoreTask implements CommandLineRunner {
    private final UserClient userClient;
    private final JmapClient jmapClient;
    private final ObjectMapper json;
    @Value("${user.defaultPassword}")
    private String password;
    @Value("classpath:gpg.pub")
    private Resource gpgPub;
    private String pgpValue;

    @PostConstruct
    @SneakyThrows
    void setGpgPub() {
        pgpValue = new String(gpgPub.getInputStream().readAllBytes(), StandardCharsets.UTF_8).replace("\n", "\\n");
    }

    @Override
    public void run(String... args) throws Exception {
        execute();
    }

    public void execute() {
        UserList user = userClient.lists();
        var counter = new AtomicInteger(0);
        user.forEach(u -> createKeyStore(u, counter));
    }

    @SneakyThrows
    private void createKeyStore(User user, AtomicInteger counter) {
        try {
            String authorization = Utils.genAuthorization(user, password);
            var session = jmapClient.session(authorization);
            String accountId = session.getAccount().getPgpAccountId();
            var jsonNode = json.readTree(getRequest(accountId));
            var createPGP = jmapClient.createKeystore(authorization, jsonNode);
            log.info("Create success, user={}, {}", user.getUsername(), createPGP.toString());
            counter.incrementAndGet();
        } catch (Exception exception) {
            log.error("Create fail, user={}, {}", user.getUsername(), exception.getMessage());
        }
    }

    private String getRequest(String accountId) {
        return String.format("{\n" +
                "  \"using\": [\"urn:ietf:params:jmap:core\", \"com:linagora:params:jmap:pgp\"],\n" +
                "  \"methodCalls\": [\n" +
                "    [\"Keystore/set\", {\n" +
                "      \"accountId\": \"%s\",\n" +
                "      \"create\": {\n" +
                "        \"K87\": {\n" +
                "          \"key\": \"%s\"\n" +
                "        }\n" +
                "      }\n" +
                "    }, \"c1\"]\n" +
                "  ]\n" +
                "}", accountId, pgpValue);
    }
}
