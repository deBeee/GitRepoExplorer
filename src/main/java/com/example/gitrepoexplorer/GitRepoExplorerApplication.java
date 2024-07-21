package com.example.gitrepoexplorer;

import com.example.gitrepoexplorer.infrastructure.security.jwt.JwtConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(value = {JwtConfigurationProperties.class})
public class GitRepoExplorerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitRepoExplorerApplication.class, args);
    }

}
