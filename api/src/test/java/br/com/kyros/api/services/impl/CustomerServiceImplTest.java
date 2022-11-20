package br.com.kyros.api.services.impl;

import br.com.kyros.api.dtos.CustomerDto;
import br.com.kyros.api.models.CustomerModel;
import br.com.kyros.api.repositories.CustomerRepository;
import br.com.kyros.api.services.exceptions.DataIntegrityViolationException;
import br.com.kyros.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceImplTest {

    private static final Integer ID = 1;
    private static final Integer INDEX = 0;
    private static final String NAME = "Fellipe";
    private static final String EMAIL = "fellipe01235@gmail.com";
    private static final String CPF = "123.321.556-85";
    private static final String PHONE = "38998213077";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate BIRTHDATE = LocalDate.parse("18/11/1998", formatter);

    private static final String OBJECT_NOT_FOUND = "Object not found";
    private static final String EMAIL_ALREADY_REGISTERED = "Email already registered";
    private static final String CPF_ALREADY_REGISTERED = "Cpf already registered";

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper mapper;

    private CustomerModel customerModel;
    private CustomerDto customerDto;
    private Optional<CustomerModel> optionalCustomerModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCustomer();
    }


    @Test
    void whenFindByIdThenReturnAnCustomerInstance() {
        when(customerRepository.findById(anyInt())).thenReturn(optionalCustomerModel);

        CustomerModel response = customerService.findById(ID);

        assertNotNull(response);

        assertEquals(CustomerModel.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(CPF, response.getCpf());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PHONE, response.getPhone());
        assertEquals(BIRTHDATE, response.getBirthDate());
    }


    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {

        when(customerRepository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));
        try {
            customerService.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
        }
    }


    @Test
    void whenFindAllThenReturnAnListOfCustomer() {
        when(customerRepository.findAll()).thenReturn(List.of(customerModel));

        List<CustomerModel> response = customerService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(CustomerModel.class, response.get(INDEX).getClass());

        assertEquals(ID,        response.get(INDEX).getId());
        assertEquals(NAME,      response.get(INDEX).getName());
        assertEquals(CPF,       response.get(INDEX).getCpf());
        assertEquals(EMAIL,     response.get(INDEX).getEmail());
        assertEquals(PHONE,     response.get(INDEX).getPhone());
        assertEquals(BIRTHDATE, response.get(INDEX).getBirthDate());
    }


    @Test
    void whenCreateThenReturnSuccess() {
        when(customerRepository.save(any())).thenReturn(customerModel);

        CustomerModel response = customerService.create(customerDto);

        assertNotNull(response);
        assertEquals(CustomerModel.class, response.getClass());

        assertEquals(ID,        response.getId());
        assertEquals(NAME,      response.getName());
        assertEquals(CPF,       response.getCpf());
        assertEquals(EMAIL,     response.getEmail());
        assertEquals(PHONE,     response.getPhone());
        assertEquals(BIRTHDATE, response.getBirthDate());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationExceptionEmail() {
        when(customerRepository.findByEmail(anyString())).thenReturn(optionalCustomerModel);

        try{
            optionalCustomerModel.get().setId(2);
            customerService.create(customerDto);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(EMAIL_ALREADY_REGISTERED, ex.getMessage());
        }
    }


    @Test
    void whenCreateThenReturnAnDataIntegrityViolationExceptionCpf() {
        when(customerRepository.findByCpf(anyString())).thenReturn(optionalCustomerModel);

        try{
            optionalCustomerModel.get().setId(2);
            customerService.create(customerDto);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(CPF_ALREADY_REGISTERED, ex.getMessage());
        }
    }


    @Test
    void whenUpdateThenReturnSuccess() {
        when(customerRepository.findById(anyInt())).thenReturn(optionalCustomerModel);
        when(customerRepository.save(any())).thenReturn(customerModel);

        CustomerModel response = customerService.update(customerDto);

        assertNotNull(response);
        assertEquals(CustomerModel.class, response.getClass());
        assertEquals(ID,        response.getId());
        assertEquals(NAME,      response.getName());
        assertEquals(CPF,       response.getCpf());
        assertEquals(EMAIL,     response.getEmail());
        assertEquals(PHONE,     response.getPhone());
        assertEquals(BIRTHDATE, response.getBirthDate());
    }


    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationExceptionEmail() {
        when(customerRepository.findByEmail(anyString())).thenReturn(optionalCustomerModel);

        try{
            optionalCustomerModel.get().setId(2);
            customerService.create(customerDto);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(EMAIL_ALREADY_REGISTERED, ex.getMessage());
        }
    }


    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationExceptionCpf() {
        when(customerRepository.findByCpf(anyString())).thenReturn(optionalCustomerModel);

        try{
            optionalCustomerModel.get().setId(2);
            customerService.create(customerDto);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(CPF_ALREADY_REGISTERED, ex.getMessage());
        }
    }


    @Test
    void deleteWithSuccess() {
        when(customerRepository.findById(anyInt())).thenReturn(optionalCustomerModel);
        doNothing().when(customerRepository).deleteById(anyInt());
        customerService.delete(ID);
        verify(customerRepository, times(1)).deleteById(anyInt());
    }


    @Test
    void whenDeleteThenReturnObjectNotFoundException() {
        when(customerRepository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));
        try {
            customerService.delete(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
        }
    }

    private void startCustomer() {
        customerModel = new CustomerModel(ID, NAME, CPF, BIRTHDATE, EMAIL, PHONE);
        customerDto = new CustomerDto(ID, NAME, CPF, BIRTHDATE, EMAIL, PHONE);
        optionalCustomerModel = Optional.of(new CustomerModel(ID, NAME, CPF, BIRTHDATE, EMAIL, PHONE));
    }
}