package com.prasad.Login.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.prasad.Login.Dto.AdminDto;
import com.prasad.Login.Dto.CustomerDto;
import com.prasad.Login.Entity.Login;
import com.prasad.Login.Exception.EmailNotFoundException;
import com.prasad.Login.Exception.InvalidPasswordException;
import com.prasad.Login.Repository.LoginRepository;
import com.prasad.Login.Service.LoginServiceImpl;

public class LoginServiceImplTest {

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LoginServiceImpl loginService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Successful() {
    	  // Arrange
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("password");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("test@example.com");
        customerDto.setPassword("password");

        ResponseEntity<CustomerDto> responseEntity = new ResponseEntity<>(customerDto, HttpStatus.OK);

        when(restTemplate.getForEntity(eq("http://localhost:8082/getCustomerByEmail/test@example.com"), eq(CustomerDto.class)))
                .thenReturn(responseEntity);

        // Act
        boolean result = loginService.login(login);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testLogin_InvalidEmail() {
        // Arrange
        Login login = new Login();
        login.setEmail("invalid@example.com");
        login.setPassword("password");

        when(restTemplate.getForEntity(eq("http://localhost:8082/getCustomerByEmail/invalid@example.com"), eq(CustomerDto.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act & Assert
        assertThrows(EmailNotFoundException.class, () -> loginService.login(login));
    }

    @Test
    public void testLogin_InvalidPassword() {
        // Arrange
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("wrongpassword");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("test@example.com");
        customerDto.setPassword("password");

        ResponseEntity<CustomerDto> responseEntity = new ResponseEntity<>(customerDto, HttpStatus.OK);

        when(restTemplate.getForEntity(eq("http://localhost:8082/getCustomerByEmail/test@example.com"), eq(CustomerDto.class)))
                .thenReturn(responseEntity);

        // Act & Assert
        assertThrows(InvalidPasswordException.class, () -> loginService.login(login));
    }

    @Test
    public void testAdminLogin_Successful1() {
        // Arrange
        Login login = new Login();
        login.setEmail("admin@example.com");
        login.setPassword("adminpassword");

        AdminDto adminDto = new AdminDto();
        adminDto.setEmail("admin@example.com");
        adminDto.setPassword("adminpassword");

        ResponseEntity<AdminDto> responseEntity = new ResponseEntity<>(adminDto, HttpStatus.OK);

        when(restTemplate.getForEntity(eq("http://localhost:8084/getAdminByEmail/admin@example.com"), eq(AdminDto.class)))
                .thenReturn(responseEntity);

        // Act
        boolean result = loginService.AdminLogin(login);

        // Assert
        assertTrue(result);
    }
    @Test
    public void testAdminLogin_Successful() {
        // Arrange
        Login login = new Login();
        login.setEmail("admin@example.com");
        login.setPassword("adminpassword");

        AdminDto adminDto = new AdminDto();
        adminDto.setEmail("admin@example.com");
        adminDto.setPassword("adminpassword");

        ResponseEntity<AdminDto> responseEntity = new ResponseEntity<>(adminDto, HttpStatus.OK);

        when(restTemplate.getForEntity(eq("http://localhost:8084/getAdminByEmail/admin@example.com"), eq(AdminDto.class)))
                .thenReturn(responseEntity);

        // Act
        boolean result = loginService.AdminLogin(login);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testAdminLogin_InvalidEmail() {
        // Arrange
        Login login = new Login();
        login.setEmail("invalid@example.com");
        login.setPassword("adminpassword");

        when(restTemplate.getForEntity(eq("http://localhost:8084/getAdminByEmail/invalid@example.com"), eq(AdminDto.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act & Assert
        assertThrows(EmailNotFoundException.class, () -> loginService.AdminLogin(login));
    }

    @Test
    public void testAdminLogin_InvalidPassword() {
        // Arrange
        Login login = new Login();
        login.setEmail("admin@example.com");
        login.setPassword("wrongpassword");

        AdminDto adminDto = new AdminDto();
        adminDto.setEmail("admin@example.com");
        adminDto.setPassword("adminpassword");

        ResponseEntity<AdminDto> responseEntity = new ResponseEntity<>(adminDto, HttpStatus.OK);

        when(restTemplate.getForEntity(eq("http://localhost:8084/getAdminByEmail/admin@example.com"), eq(AdminDto.class)))
                .thenReturn(responseEntity);

        // Act & Assert
        assertThrows(InvalidPasswordException.class, () -> loginService.AdminLogin(login));
    }
}