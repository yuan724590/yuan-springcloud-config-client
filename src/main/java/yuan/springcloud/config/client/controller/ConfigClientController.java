package yuan.springcloud.config.client.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//等同于@Scope("refresh")
@RefreshScope
public class ConfigClientController {

    @Value("${sc.springcloud.book.config}")
    private String config;

    @RequestMapping("/getConfig")
    public String getConfig() {
        return config;
    }
}
