package org.saumilp.tutorials.repositories;

import org.saumilp.tutorials.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * Repository interface
 */
public interface CustomerRepository extends Repository<Customer,String> {
    Customer findById(String id);
    Page findAll(Pageable pageable);
}
