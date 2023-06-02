package com.mindhub.homebanking.models;

        import javax.persistence.Entity;
        import javax.persistence.Id;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import org.hibernate.annotations.GenericGenerator;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    Long id;
    String firstName;
    String lastName;
    String eMail;
    public Client(){}
    public  Client(String first, String last, String email){
        firstName=first;
        lastName=last;
        eMail=email;
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
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", eMail='" + eMail + '\'' +
                '}';
    }


}
