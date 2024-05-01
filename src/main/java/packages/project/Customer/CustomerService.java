package packages.project.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packages.project.Admin.Admin;

@Service
public class CustomerService {

    CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    public Customer getCustomer(Integer loginId){
        Customer customer = customerRepository.getCustomerByCustomerId(customerRepository.getCustomerIdByloginId(loginId));
        return customer;
    }

    public void save(Customer existingCustomer) {
        customerRepository.save(existingCustomer);
    }
}
