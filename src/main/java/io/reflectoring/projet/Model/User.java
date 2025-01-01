package io.reflectoring.projet.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
@Data
@NoArgsConstructor // Permet de générer un constructeur sans arguments
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email // Ajout de la validation d'email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // Constructeur personnalisé
    public User(@NotBlank @Size(min = 3, max = 20) String username,
                @NotBlank @Size(max = 50) @Email String email,
                String encode) {
        this.username = username;
        this.email = email;
        this.password = encode;
    }

    // Constructeur par copie (si nécessaire)
    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.email = user.email;
        this.password = user.password;
        this.roles = new HashSet<>(user.roles); // Copie des rôles
    }

    public void setRoles(Set<javax.management.relation.Role> roles) {

    }
}
