package com.bancob.bancobservice.bancoB;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

// API layer (capa)
@RequestMapping(path = "/bancob")
@RestController
public class BankController {
    @Autowired
    private BankService bankService;

    // CLIENTES

    @GetMapping("/clientes")
    public List<Client> getClients() {
        return bankService.getClients(); 
    }

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

    @PutMapping("/cuentas/deposito/{accountId}/{amount}")
    public void deposit(@PathVariable String accountId, @PathVariable Double amount) {
        bankService.deposit(accountId, amount);
    }

    @PutMapping("/cuentas/retiro/{accountId}/{amount}")
    public void withdraw(@PathVariable String accountId, @PathVariable Double amount) {
        bankService.withdraw(accountId, amount);
    }

    @PutMapping("/cuentas/{accountId}") 
    public void updateContract(
        @PathVariable("accountId") String accountId, 
        @RequestParam("contract") MultipartFile contractFile) throws IOException {
        String contractContent = new String(contractFile.getBytes(), StandardCharsets.UTF_8);
        bankService.updateContract(accountId, contractContent);
    }

    // CLIENTES - OTROS BANCOS

    @GetMapping("/clientes/otros/{bankId}")
    public Mono<List<Client>> getClientsFromOtherBank(
        @PathVariable String bankId) {
        return bankService.callGetClientsFromOtherBank(bankId);
    }

    @PutMapping("/clientes/otros/{bankId}/{clientId}")
    public Mono<Void> updateClientOnOtherBank(
        @PathVariable String bankId, 
        @PathVariable Long clientId, 
        @RequestParam(required = false) String name, 
        @RequestParam(required = false) String email) {
        return bankService.callUpdateClientOnOtherBank(bankId, clientId, name, email);
    }

    // CUENTAS - OTROS BANCOS

    @GetMapping("/cuentas/otros/{bankId}")
    public Mono<List<Account>> getAccountsFromOtherBank(
        @PathVariable String bankId) {
        return bankService.callGetAccountsFromOtherBank(bankId);
    }
    
    @PutMapping("/cuentas/deposito/otros/{bankId}/{accountId}/{amount}")
    public Mono<Void> depositToOtherBank(
        @PathVariable String bankId, 
        @PathVariable String accountId, 
        @PathVariable Double amount) {
        return bankService.callDepositToOtherBank(bankId, accountId, amount);
    }

    @PutMapping("/cuentas/retiro/otros/{bankId}/{accountId}/{amount}")
    public Mono<Void> withdrawFromOtherBank(
        @PathVariable String bankId, 
        @PathVariable String accountId, 
        @PathVariable Double amount) {
        return bankService.callWithdrawFromOtherBank(bankId, accountId, amount);
    }

    @PutMapping("/cuentas/contrato/otros/{bankId}/{accountId}/{contract}")
    public Mono<Void> updateContractOnOtherBank(
        @PathVariable String bankId, 
        @PathVariable String accountId, 
        @PathVariable String contract) {
        return bankService.callUpdateContractOnOtherBank(bankId, accountId, contract);
    }

}
