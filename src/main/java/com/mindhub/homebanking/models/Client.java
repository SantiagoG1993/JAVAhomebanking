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
    Set<Account> account= new HashSet<>();

    public Set<Account> getAccount() {
        return account;
    }
    public void addAccount(Account account){
        account.setClient(this);
        this.account.add(account);
    }

    public Client(){}
    public  Client(String firstName, String lastName, String eMail){
        this.firstName=firstName;
        this.lastName=lastName;
        this.eMail=eMail;
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
    @Override
    public String toString() {
        return "Client{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", eMail='" + eMail + '\'' +
                '}';
    }


}
