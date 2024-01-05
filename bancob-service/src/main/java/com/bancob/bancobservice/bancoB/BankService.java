package com.bancob.bancobservice.bancoB;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class BankService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    // Ver
    private final WebClientConfig webClientConfig;

    public BankService(ClientRepository clientRepository, AccountRepository accountRepository, WebClientConfig webClientConfig) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.webClientConfig = webClientConfig;
    }

    // CLIENTES

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    /* 
    public void addNewClient(Client client) {
        clientRepository.save(client);
    }

    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    } 
    */

    public void updateClient(Long clientId, String name, String email) {
        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        if (name != null) {
            client.setName(name);
        }
        if (email != null) {
            client.setEmail(email);
        }
    }

    // CUENTAS

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    /* 
    public void addNewAccount(Account account) {
        accountRepository.save(account);
    }

    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
    } 
    */

    @Transactional
    public void updateContract(String accountId, String contract) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("Cuenta con id " + accountId + " no existe"));
        account.setContract(contract);
        accountRepository.save(account);
    }

    public void deposit(String accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("Cuenta con id " + accountId + " no existe"));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void withdraw(String accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("Cuenta con id " + accountId + " no existe"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Saldo insuficiente");
        }
        account.setBalance(account.getBalance() - amount);
    }
}
