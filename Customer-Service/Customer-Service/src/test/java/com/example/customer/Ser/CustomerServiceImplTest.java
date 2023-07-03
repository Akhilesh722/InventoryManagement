package com.example.customer.Ser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.customer.Dto.ProductDto;
import com.example.customer.Entity.Customer;
import com.example.customer.Exception.CustomerIdNotFoundException;
import com.example.customer.Exception.CustomerNotFoundException;
import com.example.customer.Exception.ProductNotFoundException;
import com.example.customer.Repository.ICustomerRepostiory;
import com.example.customer.Service.CustomerServiceImpl;

@SpringBootTest
public class CustomerServiceImplTest {

    @Mock
    private ICustomerRepostiory customerRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCustomer_CustomerDoesNotExist() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");

        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer addedCustomer = customerService.addCustomer(customer);

        assertEquals(customer, addedCustomer);
        verify(customerRepository, times(1)).findByEmail(customer.getEmail());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testAddCustomer_CustomerAlreadyExists() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");

        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.addCustomer(customer);
        });

        verify(customerRepository, times(1)).findByEmail(customer.getEmail());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void testDeleteCustomer_CustomerExists() {
        int customerId = 1;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer deletedCustomer = customerService.deleteCustomer(customerId);

        assertEquals(customer, deletedCustomer);
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    public void testDeleteCustomer_CustomerDoesNotExist() {
        int customerId = 1;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.deleteCustomer(customerId);
        });

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).deleteById(customerId);
    }

    // Add test methods for other methods in the CustomerServiceImpl class
    // such as updateCustomer, getCustomers, getCustomerByid, getCustomerEmail, etc.
    @Test
    public void testUpdateCustomer_CustomerExists() {
        int customerId = 1;
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(customerId);
        existingCustomer.setEmail("existing@example.com");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setEmail("updated@example.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

        Customer result = customerService.updateCustomer(customerId, updatedCustomer);

        assertEquals(updatedCustomer.getEmail(), result.getEmail());
        assertEquals(existingCustomer.getFirstName(), result.getFirstName());
        assertEquals(existingCustomer.getLastName(), result.getLastName());
        assertEquals(existingCustomer.getMobileNumber(), result.getMobileNumber());
        assertEquals(existingCustomer.getPassword(), result.getPassword());
        assertEquals(existingCustomer.getCustomerName(), result.getCustomerName());

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    public void testUpdateCustomer_CustomerDoesNotExist() {
        int customerId = 1;
        Customer updatedCustomer = new Customer();
        updatedCustomer.setEmail("updated@example.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomer(customerId, updatedCustomer);
        });

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).save(any(Customer.class));
    }
    @Test
    public void testGetCustomers_ReturnsListOfCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());
        customers.add(new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getCustomers();

        assertEquals(customers.size(), result.size());
        assertIterableEquals(customers, result);

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetCustomers_ReturnsEmptyListWhenNoCustomers() {
        List<Customer> customers = new ArrayList<>();

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getCustomers();

        assertTrue(result.isEmpty());

        verify(customerRepository, times(1)).findAll();
    }
    @Test
    public void testGetCustomerById_ReturnsCustomerWhenFound() {
        int customerId = 1;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerByid(customerId);

        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());

        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    public void testGetCustomerById_ThrowsExceptionWhenNotFound() {
        int customerId = 1;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerByid(customerId);
        });

        verify(customerRepository, times(1)).findById(customerId);
    }
    @Test
    public void testGetCustomerEmail_ReturnsEmailWhenFound() {
        int customerId = 1;
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setEmail(email);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        String result = customerService.getCustomerEmail(customerId);

        assertNotNull(result);
        assertEquals(email, result);

        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    public void testGetCustomerEmail_ThrowsExceptionWhenNotFound() {
        int customerId = 1;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerIdNotFoundException.class, () -> {
            customerService.getCustomerEmail(customerId);
        });

        verify(customerRepository, times(1)).findById(customerId);
    }
    @Test
    public void testGetCustomerPassword_ReturnsPasswordWhenFound() {
        int customerId = 1;
        String password = "password123";
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setPassword(password);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        String result = customerService.getCustomerPassword(customerId);

        assertNotNull(result);
        assertEquals(password, result);

        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    public void testGetCustomerPassword_ThrowsExceptionWhenNotFound() {
        int customerId = 1;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerIdNotFoundException.class, () -> {
            customerService.getCustomerPassword(customerId);
        });

        verify(customerRepository, times(1)).findById(customerId);
    }


}
