package com.mndev.spring.database.repository;

import com.mndev.spring.database.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Integer> {

     @Query("select c from Company c " +
             "join fetch c.locales cl " +
             "where c.name = :name")
     Optional<Company> findByName(String name);

     void delete(Company entity);

}

