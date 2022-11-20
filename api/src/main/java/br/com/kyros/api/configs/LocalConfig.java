package br.com.kyros.api.configs;

import br.com.kyros.api.models.CustomerModel;
import br.com.kyros.api.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private CustomerRepository customerRepository;

    @Bean
    public void startDB() throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        CustomerModel customerModel1 = new CustomerModel(null,"Fellipe", "123.321.556-85",
                LocalDate.parse("18/11/1998", formatter), "fellipe01235@gmail.com", "38998213077");
        CustomerModel customerModel2 = new CustomerModel(null,"João", "123.456.789-58",
                LocalDate.parse("06/11/2010", formatter), "joao@gmail.com", "38999120377");

        customerRepository.saveAll(List.of(customerModel1, customerModel2));
    }
}
