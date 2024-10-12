package OrderService.controller;

import OrderService.entity.Customers;
import OrderService.repository.CustomersRepository;
import OrderService.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomersController {
    @Autowired
    private  CustomersRepository customersRepository;
    @Autowired
    private CustomerService customerService;


    @GetMapping
    public List<Customers> listarClientes() {
        return customersRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customers obtenerCliente(@PathVariable Long id) {
        return customersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }


    @PostMapping
    public ResponseEntity<Customers> createCustomer(@RequestBody Customers customer) {
        Customers savedCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
}
