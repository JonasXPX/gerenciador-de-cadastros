package br.com.jonas.customer.manager.customer.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @JsonBackReference
    @JoinColumn(name = "id_customer")
    @ManyToOne
    private Customer customer;

}
