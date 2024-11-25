package com.dev.picpaysimplificado.repositories;

import com.dev.picpaysimplificado.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
