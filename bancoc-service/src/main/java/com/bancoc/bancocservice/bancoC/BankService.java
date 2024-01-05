package com.bancoc.bancocservice.bancoC;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BankService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    // Webclient: Intercomunicacion de servicios
    // private WebClient webClient;
    private final Map<String, WebClient> bankWebClients;

    public BankService(ClientRepository clientRepository, AccountRepository accountRepository, WebClient.Builder webClientBuilder) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        bankWebClients = new HashMap<>();
        bankWebClients.put("bancoa", webClientBuilder.baseUrl("http://localhost:8083/bancoa").build());
        bankWebClients.put("bancob", webClientBuilder.baseUrl("http://localhost:8082/bancob").build());
    }

    // CLIENTES

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void updateClient(Long clientId, String name, String email) {
        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        if (name != null) {
            client.setName(name);
        }
        if (email != null) {
            client.setEmail(email);
        }
        clientRepository.save(client);
    }

    // CUENTAS

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public void deposit(String accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("Cuenta con id " + accountId + " no existe"));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Transactional
    public void withdraw(String accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("Cuenta con id " + accountId + " no existe"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Saldo insuficiente");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    @Transactional
    public void updateContract(String accountId, String contract) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("Cuenta con id " + accountId + " no existe"));
        account.setContract(contract);
        accountRepository.save(account);
    }

    // CLIENTES - OTROS BANCOS

    public Mono<List<Client>> callGetClientsFromOtherBank(String bankId) {
        WebClient webClient = bankWebClients.get(bankId);
        if (webClient == null) {
            throw new IllegalArgumentException("Unknown bank: " + bankId);
        }
        return webClient.get()
                .uri("/clientes")
                .retrieve()
                .bodyToFlux(Client.class)
                .collectList();
    }

    public Mono<Void> callUpdateClientOnOtherBank(String bankId, Long clientId, String name, String email) {
        WebClient webClient = bankWebClients.get(bankId);
        if (webClient == null) {
            throw new IllegalArgumentException("Unknown bank: " + bankId);
        }
        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                    .path("/clientes/{clientId}")
                    .queryParam("name", name)
                    .queryParam("email", email)
                    .build(clientId))
                .retrieve()
                .bodyToMono(Void.class);
    }

    // CUENTAS - OTROS BANCOS

    public Mono<List<Account>> callGetAccountsFromOtherBank(String bankId) {
        WebClient webClient = bankWebClients.get(bankId);
        if (webClient == null) {
            throw new IllegalArgumentException("Unknown bank: " + bankId);
        }
        return webClient.get()
                .uri("/cuentas")
                .retrieve()
                .bodyToFlux(Account.class)
                .collectList();
    }

    public Mono<Void> callDepositToOtherBank(String bankId, String accountId, Double amount) {
        WebClient webClient = bankWebClients.get(bankId);
        if (webClient == null) {
            throw new IllegalArgumentException("Unknown bank: " + bankId);
        }
        return webClient.put()
                .uri("/cuentas/deposito/{accountId}/{amount}", accountId, amount)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> callWithdrawFromOtherBank(String bankId, String accountId, Double amount) {
        WebClient webClient = bankWebClients.get(bankId);
        if (webClient == null) {
            throw new IllegalArgumentException("Unknown bank: " + bankId);
        }
        return webClient.put()
                .uri("/cuentas/retiro/{accountId}/{amount}", accountId, amount)
                .retrieve()
                .bodyToMono(Void.class);
    }
    
    public Mono<Void> callUpdateContractOnOtherBank(String bankId, String accountId, String contract) {
        WebClient webClient = bankWebClients.get(bankId);
        if (webClient == null) {
            throw new IllegalArgumentException("Unknown bank: " + bankId);
        }
        return webClient.put()
                .uri("/cuentas/contrato/{accountId}/{contract}", accountId, contract)
                .retrieve()
                .bodyToMono(Void.class);
    }
    
}
