package com.mindhub.homebanking.models;
        import javax.persistence.*;
        import org.hibernate.annotations.GenericGenerator;
        import java.util.HashSet;
        import java.util.Set;
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String eMail;
    @OneToMany(mappedBy = "client", fetch=FetchType.EAGER)
    private Set<Account> accounts= new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<ClientLoan> clientLoans=new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<Card> cards=new HashSet<>();


    public Client(){}
    public  Client(String firstName, String lastName, String eMail){
        this.firstName=firstName;
        this.lastName=lastName;
        this.eMail=eMail;
    }

    public Set<Card> getCards() {
        return cards;
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
    public Long getId() {
        return id;
    }
    public String geteMail(){
        return eMail;
    }
    public void seteMail(String eMail){
        this.eMail=eMail;
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
                ", eMail='" + eMail + '\'' +
                '}';
    }


}
