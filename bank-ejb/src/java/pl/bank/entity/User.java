/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bank.entity;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 *
 * @author carbolymer
 */
@Entity
@Table(name="users")
@NamedQueries({
    @NamedQuery(
       name = "getUsers", 
       query="SELECT u FROM User u"
    ),
    @NamedQuery(
       name = "findByLogin", 
       query="SELECT u FROM User u WHERE u.login = :login"
    ),        
    @NamedQuery(
       name = "findByType", 
       query="SELECT u FROM User u WHERE u.userType = :type"
    ),
    @NamedQuery(
       name = "findById", 
       query="SELECT u FROM User u WHERE u.id = :id"
    ),       
    @NamedQuery(
        name = "findUsers",
        query="SELECT u FROM User u WHERE u.name LIKE :term OR u.surname LIKE :term OR u.login LIKE :term"
    ),
    @NamedQuery(
        name = "findByLoginPassword",
        query="SELECT u FROM User u WHERE u.login = :login AND u.password = :password"
    )        
})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String surname;
    private String login;
    private String password;
    private String address;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    
    @OneToMany(mappedBy = "user", fetch= FetchType.EAGER)
    @OrderBy("number ASC") 
    private Set<Account> accounts = new HashSet<Account>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.bank.entity.Klient[ id=" + id + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = UserFacade.sha512(password);
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the accounts
     */
    public Set<Account> getAccounts() {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * @return the userType
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
