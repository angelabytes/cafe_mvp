package org.perscholas.cafe_mvp.service;

import org.perscholas.cafe_mvp.model.Cart;
import org.perscholas.cafe_mvp.model.Customer;
import org.perscholas.cafe_mvp.repository.CartRepository;
import org.perscholas.cafe_mvp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public Customer registerCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer savedCustomer = customerRepository.save(customer);

        //Create cart
        Cart cart = new Cart();
        cart.setCustomer(savedCustomer);
        cartService.saveCart(cart);
        return savedCustomer;
    }

    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Long id, Customer customerinfo) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(customerinfo.getName());
                    customer.setEmail(customerinfo.getEmail());
                    return customerRepository.save(customer);
                }).orElse(null);
    }
}
