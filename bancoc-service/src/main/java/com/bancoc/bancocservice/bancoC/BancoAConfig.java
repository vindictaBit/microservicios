package com.bancoc.bancocservice.bancoC;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BancoAConfig {
    @Bean
    CommandLineRunner commandLineRunner(
        ClientRepository clientRepository, 
        AccountRepository accountRepository) {
        return args -> {
            Client client1 = new Client(
                "Pedro", 
                "pedro@gmail.com"
            );

            Client client2 = new Client(
                "Juan", 
                "juan@gmail.com"
            );

            Client client3 = new Client(
                "Jose", 
                "jose@gmail.com"
            );

            clientRepository.saveAll(
                List.of(client1, client2, client3)
            );

            byte[] contractBytes = Files.readAllBytes(Paths.get("C:/Users/V/Desktop/contrato.txt"));
            String contractContent = new String(contractBytes, StandardCharsets.UTF_8);

            Account account1 = new Account(
                "001234", 
                contractContent,
                1000.0
            );
            account1.setClient(client2);

            Account account2 = new Account(
                "001235", 
                contractContent,
                2000.0
            );
            account2.setClient(client1);

            Account account3 = new Account(
                "001236", 
                contractContent,
                3000.0
            );
            account3.setClient(client3);

            accountRepository.saveAll(
                List.of(account1, account2, account3)
            );
        };
    }
}

/* Account account1 = new Account(
                "001234", 
                "Este contrato estipula lo siguiente: ...",
                1000.0
            );
            account1.setClient(client1);

            Account account2 = new Account(
                "001235", 
                "Este contrato estipula lo siguiente: ...",
                2000.0
            );
            account2.setClient(client2);

            Account account3 = new Account(
                "001236", 
                "Este contrato estipula lo siguiente: ...",
                3000.0
            );
            account3.setClient(client3); */