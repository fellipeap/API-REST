package br.com.kyros.api.services.impl;

import br.com.kyros.api.dtos.CustomerDto;
import br.com.kyros.api.models.CustomerModel;
import br.com.kyros.api.repositories.CustomerRepository;
import br.com.kyros.api.services.CustomerService;
import br.com.kyros.api.services.exceptions.DataIntegrityViolationException;
import br.com.kyros.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CustomerModel findById(Integer id)  {
        Optional<CustomerModel> customerModel = customerRepository.findById(id);
        return customerModel.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    @Override
    public List<CustomerModel> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerModel create(CustomerDto customerDto) {
        findByEmail(customerDto);
        findByCpf(customerDto);
        return customerRepository.save(mapper.map(customerDto, CustomerModel.class));
    }

    @Override
    public CustomerModel update(CustomerDto customerDto) {
        findById(customerDto.getId());
        findByEmail(customerDto);
        findByCpf(customerDto);
        return customerRepository.save(mapper.map(customerDto, CustomerModel.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        customerRepository.deleteById(id);
    }

    private void findByEmail(CustomerDto customerDto) {
        Optional<CustomerModel> customerModel = customerRepository.findByEmail(customerDto.getEmail());
        if(customerModel.isPresent() && !customerModel.get().getId().equals(customerDto.getId())) {
            throw new DataIntegrityViolationException("Email already registered");
        }
    }

    private void findByCpf(CustomerDto customerDto) {
        Optional<CustomerModel> customerModel = customerRepository.findByCpf(customerDto.getCpf());
        if(customerModel.isPresent() && !customerModel.get().getId().equals(customerDto.getId())) {
            throw new DataIntegrityViolationException("Cpf already registered");
        }
    }
}
