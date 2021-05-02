package com.desafio.csp.demo.controller;

import com.desafio.csp.demo.model.Contact;
import com.desafio.csp.demo.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contact")
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    @GetMapping
    public List<Contact> list(){
        return contactRepository.findAll();
    }

    @GetMapping("/filter")
    public List<Contact> listByNameOrEmail(@RequestParam(required = false) String firstName, @RequestParam(required = false) String email){
        return firstName == null ?
                contactRepository.findAllByEmailContains(email) :
                contactRepository.findAllByFirstNameContains(firstName);

    }

    @PostMapping
    public Contact create(@RequestBody Contact contact){
        return contactRepository.save(contact);
    }

    @PutMapping("{id}")
    public ResponseEntity<Contact> update(@PathVariable long id, @RequestBody Contact contact){
        return contactRepository.findById(id)
                .map(updated -> {
                    updated.setFirstName(contact.getFirstName());
                    updated.setLastName(contact.getLastName());
                    updated.setEmail(contact.getEmail());
                    updated.setPhone(contact.getPhone());
                    var update = contactRepository.save(updated);
                    return ResponseEntity.ok().body(update);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        return contactRepository.findById(id)
                .map(find -> {
                    contactRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());

    }
}
