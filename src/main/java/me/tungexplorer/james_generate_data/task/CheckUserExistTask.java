package me.tungexplorer.james_generate_data.task;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.tungexplorer.james_generate_data.client.JmapClient;
import me.tungexplorer.james_generate_data.dto.User;
import me.tungexplorer.james_generate_data.utils.Utils;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckUserExistTask implements CommandLineRunner {

    @Autowired
    private JmapClient jmapClient;

    @Override
    public void run(String... args) throws Exception {
        List<Pair<String, String>> users = readUserFromCSV();
        users.forEach(this::checkUserExist);
    }

    private void checkUserExist(Pair<String, String> stringStringPair) {
        User username = new User(stringStringPair.getLeft());
        String password = stringStringPair.getRight();
        String authorization = Utils.genAuthorization(username, password);
        try {
            var session = jmapClient.session(authorization);
//            log.info("Success, user={}, {}", username.getUsername(), session.getAccount().toString());
        } catch (Exception e) {
            log.info("Failed, user={}, {}", username.getUsername(), e.getMessage());
        }
    }

    @SneakyThrows
    private List<Pair<String, String>> readUserFromCSV() {
        try (CSVReader reader = new CSVReader(new FileReader("/home/tungtv/workplace/JAMES/james-generate-data/users.csv"))) {
            List<String[]> r = reader.readAll();
            r.remove(0);
            return r.stream().map(e -> Pair.of(e[0], e[1]))
                .collect(Collectors.toList());
        }
    }
}
