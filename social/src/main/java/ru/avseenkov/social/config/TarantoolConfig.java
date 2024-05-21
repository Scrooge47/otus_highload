package ru.avseenkov.social.config;


import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TarantoolConfig {

    @Bean
    public TarantoolClient tarantoolClient() {
        return TarantoolClientFactory.createClient()
                // If any addresses or an address provider are not specified,
                // the default host 127.0.0.1 and port 3301 are used
                .withAddress("127.0.0.1", 3301)
                // For connecting to a Cartridge application, use the value of cluster_cookie parameter in the init.lua file
                // .withCredentials("tarantool", "tarantool")
                // you may also specify more client settings, such as:
                // timeouts, number of connections, custom MessagePack entities to Java objects mapping, etc.
                .build();

    }
}
