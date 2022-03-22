package com.example.person.file.gateway.configuration;

import com.example.person.file.gateway.models.Event;
import com.example.person.file.gateway.models.Person;
import com.example.person.file.gateway.services.PersonService;
import com.example.person.file.gateway.utils.PersonFileReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.ChannelSftp;
import com.opencsv.CSVReader;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.filters.AbstractFileListFilter;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.handler.advice.ExpressionEvaluatingRequestHandlerAdvice;
import org.springframework.integration.metadata.SimpleMetadataStore;
import org.springframework.integration.sftp.filters.SftpPersistentAcceptOnceFileListFilter;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.inbound.SftpStreamingMessageSource;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.integration.transformer.StreamTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.InputStream;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.function.Supplier;

@Configuration
@Getter
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileTransferConfiguration {

    static String REMOTE_DIRECTORY = "/upload";
    SftpConfiguration sftpConfiguration;
    PersonFileReader personFileReader;
    PersonService personService;
    ObjectMapper objectMapper = new ObjectMapper();

    public FileTransferConfiguration(SftpConfiguration sftpConfiguration,
                                     PersonFileReader personFileReader,
                                     PersonService personService) {
        this.sftpConfiguration = sftpConfiguration;
        this.personFileReader = personFileReader;
        this.personService = personService;
    }

    @Bean
    public SftpRemoteFileTemplate template() {
        return new SftpRemoteFileTemplate(sftpConfiguration.sftSessionFactory());
    }

    @Bean(name = "person-csv-file")
    public MessageChannel personChannel() {
        return new DirectChannel();
    }

    @Bean(name = "person-data")
    public MessageChannel personDataChannel() {
        return new DirectChannel();
    }

    @Bean(name = "person-process")
    public MessageChannel personProcessChannel() {
        return new DirectChannel();
    }


    @Bean
    @InboundChannelAdapter(channel = "person-csv-file", poller = @Poller(fixedDelay = "1000"))
    public MessageSource<InputStream> sftpSource() {
        SftpStreamingMessageSource messageSource = new SftpStreamingMessageSource(template(), Comparator.comparing(file -> getCsvFileName(file.getFilename())));
        messageSource.setRemoteDirectory(REMOTE_DIRECTORY);
        messageSource.setFilter(compositeFilters());
        messageSource.setMaxFetchSize(1);
        return messageSource;
    }

    @Bean
    public CompositeFileListFilter<ChannelSftp.LsEntry> compositeFilters() {
        CompositeFileListFilter<ChannelSftp.LsEntry> compositeFileListFilter = new CompositeFileListFilter<>();
        compositeFileListFilter.addFilter(
                new AbstractFileListFilter<>() {
                    @Override
                    public boolean accept(ChannelSftp.LsEntry file) {
                        return (file.getFilename().endsWith(".csv"));
                    }
                }
        );

        compositeFileListFilter.addFilter(
                new SftpPersistentAcceptOnceFileListFilter(new SimpleMetadataStore(), "person-process")
        );
        return compositeFileListFilter;
    }

    private int getCsvFileName(String filename) {
        int val = 0;
        if (filename.endsWith(".csv")) {
            String fileWithOutCSVExtension = filename.substring(0, filename.length() - 4);
            String[] section = fileWithOutCSVExtension.split("-");
            val = Integer.parseInt(section[1]);
        } else {
            log.warn("Nom fichier {} non conforme", filename);
        }
        return val;
    }

    @Bean
    public SftpInboundFileSynchronizer sftpInboundFileSynchronizer() {
        var fileSynchronizer = new SftpInboundFileSynchronizer(sftpConfiguration.sftSessionFactory());
        fileSynchronizer.setDeleteRemoteFiles(false);
        fileSynchronizer.setRemoteDirectory(REMOTE_DIRECTORY);
        fileSynchronizer.setFilter(new SftpSimplePatternFileListFilter(".csv"));
        return fileSynchronizer;
    }

    @Bean
    @Transformer(inputChannel = "person-csv-file", outputChannel = "person-data")
    public StreamTransformer transformer() {
        return new StreamTransformer("UTF-8");
    }


    @ServiceActivator(inputChannel = "person-data", adviceChain = "after")
    @Bean
    public MessageHandler messageHandler() {
        return message -> {
            CSVReader csvReader = new CSVReader(new StringReader(message.getPayload().toString()));
            csvReader.forEach(strings -> {
                try{
                    Person person = Person.builder()
                            .firstname(strings[0])
                            .lastname(strings[1])
                            .sex(strings[2])
                            .dob(new Timestamp(Long.parseLong(strings[3])))
                            .address(strings[4])
                            .phoneNumber(strings[5]).build();
                    personService.transformPersonToEvent(person);
                }
                catch (Exception ex){
                    log.error(ex.getMessage(),ex.getCause());
                }
            });
        };
    }

    @Bean
    public Supplier<Event> personSupplier(PersonService personService) {
        return personService::processEventPerson;
    }

    @Bean
    public ExpressionEvaluatingRequestHandlerAdvice after() {
        ExpressionEvaluatingRequestHandlerAdvice advice = new ExpressionEvaluatingRequestHandlerAdvice();
        advice.setPropagateEvaluationFailures(true);
        return advice;
    }

}
