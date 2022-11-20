package br.com.kyros.api.services;

import br.com.kyros.api.dtos.CustomerDto;
import br.com.kyros.api.models.CustomerModel;

import java.util.List;

public interface CustomerService {
    CustomerModel findById(Integer id);
    List<CustomerModel> findAll();
    CustomerModel create(CustomerDto obj);
    CustomerModel update(CustomerDto obj);
    void delete(Integer id);
}
