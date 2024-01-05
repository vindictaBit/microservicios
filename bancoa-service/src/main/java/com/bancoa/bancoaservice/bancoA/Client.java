package com.bancoa.bancoaservice.bancoA;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table 
public class Client {
    @Id // Data access layer
    @SequenceGenerator(
        name = "client_sequence",
        sequenceName = "client_sequence",
        allocationSize = 1
    )
    // Data access layer
    @GeneratedValue(
        generator = "client_sequence",
        strategy = jakarta.persistence.GenerationType.SEQUENCE
    )
    
    private Long id;
    private String name;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Account> accounts;

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // getters and setters

    /* public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    } */
}