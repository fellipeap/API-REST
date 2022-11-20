package br.com.kyros.api.controllers;

import br.com.kyros.api.dtos.CustomerDto;
import br.com.kyros.api.models.CustomerModel;
import br.com.kyros.api.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerControllerTest {

    private static final Integer ID       = 1;
    private static final Integer INDEX    = 0;
    private static final String NAME      = "Fellipe";
    private static final String EMAIL     = "fellipe01235@gmail.com";
    private static final String CPF       = "123.321.556-85";
    private static final String PHONE     = "38998213077";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate BIRTHDATE =  LocalDate.parse("18/11/1998", FORMATTER);

    private CustomerModel customerModel;
    private CustomerDto customerDto;

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCustomer();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(customerService.findById(anyInt())).thenReturn(customerModel);
        when(mapper.map(any(), any())).thenReturn(customerDto);

        ResponseEntity<CustomerDto> response = customerController.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(CustomerDto.class,    response.getBody().getClass());

        assertEquals(ID,        response.getBody().getId());
        assertEquals(NAME,      response.getBody().getName());
        assertEquals(CPF,       response.getBody().getCpf());
        assertEquals(EMAIL,     response.getBody().getEmail());
        assertEquals(PHONE,     response.getBody().getPhone());
        assertEquals(BIRTHDATE, response.getBody().getBirthDate());
    }

    @Test
    void whenFindAllThenReturnAListOfCustomerDto() {
        when(customerService.findAll()).thenReturn(List.of(customerModel));
        when(mapper.map(any(), any())).thenReturn(customerDto);

        ResponseEntity<List<CustomerDto>> response = customerController.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK,        response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class,      response.getBody().getClass());
        assertEquals(CustomerDto.class,    response.getBody().get(INDEX).getClass());

        assertEquals(ID,        response.getBody().get(INDEX).getId());
        assertEquals(NAME,      response.getBody().get(INDEX).getName());
        assertEquals(CPF,       response.getBody().get(INDEX).getCpf());
        assertEquals(EMAIL,     response.getBody().get(INDEX).getEmail());
        assertEquals(PHONE,     response.getBody().get(INDEX).getPhone());
        assertEquals(BIRTHDATE, response.getBody().get(INDEX).getBirthDate());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(customerService.create(any())).thenReturn(customerModel);

        ResponseEntity<CustomerDto> response = customerController.create(customerDto);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(customerService.update((customerDto))).thenReturn(customerModel);
        when(mapper.map(any(), any())).thenReturn(customerDto);

        ResponseEntity<CustomerDto> response = customerController.update(ID, customerDto);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(CustomerDto.class, response.getBody().getClass());

        assertEquals(ID,        response.getBody().getId());
        assertEquals(NAME,      response.getBody().getName());
        assertEquals(CPF,       response.getBody().getCpf());
        assertEquals(EMAIL,     response.getBody().getEmail());
        assertEquals(PHONE,     response.getBody().getPhone());
        assertEquals(BIRTHDATE, response.getBody().getBirthDate());

    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(customerService).delete(anyInt());

        ResponseEntity<CustomerDto> response = customerController.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(customerService, times(1)).delete(anyInt());
    }

    private void startCustomer(){
        customerModel = new CustomerModel(ID,NAME, CPF, BIRTHDATE, EMAIL, PHONE);
        customerDto = new CustomerDto(ID, NAME, CPF, BIRTHDATE, EMAIL, PHONE);
    }
}