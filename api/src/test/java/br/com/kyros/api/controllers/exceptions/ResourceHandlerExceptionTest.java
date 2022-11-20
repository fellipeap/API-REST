package br.com.kyros.api.controllers.exceptions;

import br.com.kyros.api.services.exceptions.DataIntegrityViolationException;
import br.com.kyros.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceHandlerExceptionTest {

    private static final String OBJECT_NOT_FOUND = "Object not found";
    private static final String EMAIL_ALREADY_REGISTERED = "Email already registered";

    @InjectMocks
    private ResourceHandlerException resourceHandlerException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardErrorException> response = resourceHandlerException
                .objectNotFound(
                new ObjectNotFoundException(OBJECT_NOT_FOUND),
                new MockHttpServletRequest());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardErrorException.class, response.getBody().getClass());
        assertEquals(404, response.getBody().getStatus());
        assertNotEquals("/customer/2", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<StandardErrorException> response = resourceHandlerException
                .dataIntegrityViolationException(
                        new DataIntegrityViolationException(EMAIL_ALREADY_REGISTERED),
                        new MockHttpServletRequest());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardErrorException.class, response.getBody().getClass());
        assertEquals(EMAIL_ALREADY_REGISTERED, response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }
}