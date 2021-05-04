package me.tungexplorer.james_generate_data.client;

import me.tungexplorer.james_generate_data.dto.UserCreateRequest;
import me.tungexplorer.james_generate_data.dto.UserList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "userClient", url = "${webAdmin.baseUrl}", configuration = WebAdminClientConfig.class)
public interface UserClient {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    UserList lists();

    @RequestMapping(value = "/users/{username}", method = RequestMethod.PUT)
    void add(@PathVariable("username") String username, @RequestBody UserCreateRequest password);
}
