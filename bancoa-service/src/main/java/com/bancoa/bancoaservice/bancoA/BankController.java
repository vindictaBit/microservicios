package com.bancoa.bancoaservice.bancoA;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// API layer (capa)
@RequestMapping(path = "/bancoa")
@RestController
public class BankController {
    @Autowired
    private BankService bankService;

    // CLIENTES

    @GetMapping("/clientes")
    public List<Client> getClients() {
        return bankService.getClients(); 
    }

    /* 
    @PostMapping("/clientes")
    public void registerNewClient(@RequestBody Client client) { 
        bankService.addNewClient(client);
    }

    @DeleteMapping(path = "/clientes/{clientId}") 
    public void deleteClient(@PathVariable("clientId") Long clientId) {
        bankService.deleteClient(clientId);
    } 
    */

    @PutMapping(path = "/clientes/{clientId}") 
    public void updateClient(
        @PathVariable("clientId") Long clientId, 
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email) {
        bankService.updateClient(clientId, name, email);
    }

    // CUENTAS

    @GetMapping("/cuentas")
    public List<Account> getAccounts() {
        return bankService.getAccounts(); 
    }

    /*
    @PostMapping("/cuentas")
    public void registerNewAccount(@RequestBody Account account) { 
        bankService.addNewAccount(account);
    }

    @DeleteMapping("/cuentas/{accountId}") 
    public void deleteAccount(@PathVariable("accountId") String accountId) {
        bankService.deleteAccount(accountId);
    } 
    */

    @PutMapping("/cuentas/{accountId}") 
    public void updateContract(
        @PathVariable("accountId") String accountId, 
        @RequestParam("contract") MultipartFile contractFile) throws IOException {
        String contractContent = new String(contractFile.getBytes(), StandardCharsets.UTF_8);
        bankService.updateContract(accountId, contractContent);
    }

    @PutMapping("/cuentas/deposito/{accountId}/{amount}")
    public void deposit(@PathVariable String accountId, @PathVariable Double amount) {
        bankService.deposit(accountId, amount);
    }

    @PutMapping("/cuentas/retiro/{accountId}/{amount}")
    public void withdraw(@PathVariable String accountId, @PathVariable Double amount) {
        bankService.withdraw(accountId, amount);
    }

}