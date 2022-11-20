package br.com.kyros.api.controllers;

import br.com.kyros.api.dtos.CustomerDto;
import br.com.kyros.api.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/customer")
@Tag(name = "Customer", description = "API to register customers")
public class CustomerController {

    public static final String ID = "/{id}";

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = ID)
    @Operation(summary = "Get the customer searched for by id. Success status 200")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<CustomerDto> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(customerService.findById(id), CustomerDto.class));
    }

    @GetMapping
    @Operation(summary = "Get all registered customers. Success status 200")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<List<CustomerDto>> findAll(){
        List<CustomerDto> customerDto = customerService.findAll()
                .stream().map(customer -> mapper.map(customer, CustomerDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(customerDto);
    }

    @PostMapping
    @Operation(summary = "Registers a customer and return the access url in the header. Success status 201")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto customerDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(customerService.create(customerDto)
                .getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = ID)
    @Operation(summary = "Update customer information. Success status 200")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<CustomerDto> update(@PathVariable Integer id, @RequestBody CustomerDto customerDto){
        customerDto.setId(id);
        return ResponseEntity.ok().body(mapper.map(customerService.update(customerDto), CustomerDto.class));
    }

    @DeleteMapping(value = ID)
    @Operation(summary = "Delete the informed customer. Success status 204")
    @ApiResponse(responseCode = "204", description = "Success")
    public ResponseEntity<CustomerDto> delete(@PathVariable Integer id){
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
