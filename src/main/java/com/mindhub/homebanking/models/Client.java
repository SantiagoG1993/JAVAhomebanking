package com.mindhub.homebanking.models;
        import javax.persistence.*;
        import org.hibernate.annotations.GenericGenerator;
        import org.springframework.security.core.GrantedAuthority;

        import java.util.HashSet;
        import java.util.List;
        import java.util.Set;
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(mappedBy = "client", fetch=FetchType.EAGER)
    private Set<Account> accounts= new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<ClientLoan> clientLoans=new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<Card> cards=new HashSet<>();

    public Client() {
    }

    public  Client(String firstName, String lastName, String email, String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email = email;
        this.password=password;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }
    public Set<ClientLoan> getLoans(){
        return clientLoans;
    }
    public Set<Account> getAccounts() {
        return accounts;
    }
    public void addAccount(Account account){
        account.setClient(this);
        this.accounts.add(account);
    }
    public void addCard(Card card){
        card.setClient(this);
        this.cards.add(card);
    }

    public Long getId() {
        return id;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName=lastName;
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    @Override
    public String toString() {
        return "Client{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", eMail='" + email + '\'' +
                '}';
    }


}
