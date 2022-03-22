package com.example.person.file.gateway.configuration;

import com.jcraft.jsch.ChannelSftp;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

@Getter
@Configuration
public class SftpConfiguration {

    @Value("${person.sftp.host}")
    private String sftpHost;

    @Value("${person.sftp.user}")
    private String sftpUser;

    @Value("${person.sftp.port}")
    private int sftpPort;

    @Value("${person.sftp.privateKey}")
    private String privateKey;

    @Value("${person.sftp.password}")
    private String sftpPassword;

    @Bean
    public SessionFactory<ChannelSftp.LsEntry> sftSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost(getSftpHost());
        factory.setPort(getSftpPort());
        factory.setUser(getSftpUser());
        factory.setPassword(getSftpPassword());
        factory.setAllowUnknownKeys(true);
        return new CachingSessionFactory<>(factory);
    }
}
