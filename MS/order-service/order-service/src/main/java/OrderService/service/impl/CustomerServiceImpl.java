package OrderService.service.impl;

import OrderService.entity.Customers;
import OrderService.repository.CustomersRepository;
import OrderService.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomersRepository customersRepository;

    @Autowired
    private final CustomersRepository customerRepository;

    @Override
    public Customers createCustomer(Customers customer) {
        return customerRepository.save(customer);
    }
}
