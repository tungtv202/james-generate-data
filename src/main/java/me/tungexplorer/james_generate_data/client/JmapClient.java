package me.tungexplorer.james_generate_data.client;

import me.tungexplorer.james_generate_data.dto.JmapSessionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "jmapClient", url = "${jmap.baseUrl}/jmap", configuration = JmapClientConfig.class)
public interface JmapClient {

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    JmapSessionResponse session(@RequestHeader("Authorization") String authorization);


    @RequestMapping(value = "", method = RequestMethod.POST)
    Object createKeystore(@RequestHeader("Authorization") String authorization, @RequestBody Object request);

}
