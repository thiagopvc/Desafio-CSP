package com.desafio.csp.demo.repository;

import com.desafio.csp.demo.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findAllByEmailContains(String email);

    List<Contact> findAllByFirstNameContains(String firstName);

}
