package br.com.jonas.customer.manager.customer.domain;

import br.com.jonas.customer.manager.config.model.Model;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "cpf"})
@NamedEntityGraph(name = "graph.Customer", attributeNodes = @NamedAttributeNode("addresses"))
public class Customer implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String cpf;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

