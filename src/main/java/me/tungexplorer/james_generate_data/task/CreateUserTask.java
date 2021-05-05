package me.tungexplorer.james_generate_data.task;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tungexplorer.james_generate_data.client.UserClient;
import me.tungexplorer.james_generate_data.dto.UserCreateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateUserTask implements CommandLineRunner {
    public static final int USER_COUNTER = 100;
    private final UserClient userClient;
    private final CreateKeyStoreTask createKeyStoreTask;

    @Value("${user.defaultPassword}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        createUser();
        createKeyStoreTask.execute();
    }

    public void createUser() {
        var userCreateRequest = new UserCreateRequest(password);
        var success = 0;
        for (int i = 1; i <= USER_COUNTER; i++) {
            try {
                userClient.add(String.format("user%s@localhost", i), userCreateRequest);
                success++;
            } catch (FeignException ex) {
                log.error(ex.getMessage());
            }
        }
        log.info("Create user finish. Total User={}", success);
    }
}
