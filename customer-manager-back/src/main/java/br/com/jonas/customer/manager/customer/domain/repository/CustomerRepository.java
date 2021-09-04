package br.com.jonas.customer.manager.customer.domain.repository;

import br.com.jonas.customer.manager.customer.domain.Customer;
import br.com.jonas.customer.manager.customer.domain.dto.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @EntityGraph("graph.Customer")
    @Query("select c from Customer c where upper(concat(c.name, c.surname, c.cpf)) like upper(concat('%', ?1, '%'))")
    Page<Customer> findAll(String search, Pageable pageable);

    @EntityGraph("graph.Customer")
    @Query("select c from Customer c " +
            "where upper(concat(c.name, c.surname, c.cpf)) like upper(concat('%', :#{#filter.search}, '%'))" +
            "and c.createdAt >= :#{#filter.startDate == null ? #filter.start  : #filter.startDate } " +
            "and c.createdAt <= :#{#filter.endDate == null ? #filter.end : #filter.endDate} ")
    Page<Customer> findAllByCustomFilter(@Param("filter") Filter filter, Pageable pageable);


    @Override
    @EntityGraph("graph.Customer")
    Optional<Customer> findById(Long id);

    Optional<Customer> findByCpf(String cpf);

}
