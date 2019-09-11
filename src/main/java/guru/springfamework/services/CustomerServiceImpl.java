package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers(String path) {
        return customerRepository.findAll()
                .stream()
                .map(c -> {
                    c.setCustomerUrl(path + "/" + c.getId());
                    CustomerDTO dto = customerMapper.customerToCustomerDTO(c);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(String path, Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return null;
        }
        CustomerDTO dto = customerMapper.customerToCustomerDTO(customer);
        dto.setCustomerUrl(path);
        return dto;
    }
}
