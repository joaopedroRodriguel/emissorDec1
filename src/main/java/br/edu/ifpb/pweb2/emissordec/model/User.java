package br.edu.ifpb.pweb2.emissordec.model;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id 
    private String username;
    private String password;
    private Boolean enabled;
    
    @OneToMany(mappedBy = "username")
    @ToString.Exclude
    List<Authority> authorities;
}
