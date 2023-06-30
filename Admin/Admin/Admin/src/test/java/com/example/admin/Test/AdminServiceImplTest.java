package com.example.admin.Test;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.admin.Entity.Admin;
import com.example.admin.Entity.Product;
import com.example.admin.exception.InvalidBookingIdException;
import com.example.admin.exception.InvalidCustomerIDException;
import com.example.admin.exception.InvalidProductIdException;
import com.example.admin.exception.NoAdminFoundException;
import com.example.admin.modal.BookingDetailsDto;
import com.example.admin.modal.CustomerDto;
import com.example.admin.modal.ProductDto;
import com.example.admin.repo.AdminRepo;
import com.example.admin.service.AdminServiceImpl;

public class AdminServiceImplTest {
    
    @Mock
    private AdminRepo adminRepo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks 
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAdmin() {
        Admin admin = new Admin();
        admin.setId(1);
        admin.setEmail("admin@example.com");

        when(adminRepo.findById(anyInt())).thenReturn(Optional.empty());
        when(adminRepo.save(any(Admin.class))).thenReturn(admin);

        Admin createdAdmin = adminService.createAdmin(admin);

        assertEquals(admin, createdAdmin);
        verify(adminRepo, times(1)).findById(anyInt());
        verify(adminRepo, times(1)).save(any(Admin.class));
    }

    @Test
    void testCreateAdmin_ExistingAdmin() {
        // Arrange
        Admin admin = new Admin();
        admin.setId(1);
        admin.setEmail("admin@example.com");
        when(adminRepo.findById(anyInt())).thenReturn(Optional.of(admin));

        // Act and Assert
        assertThrows(NoAdminFoundException.class, () -> adminService.createAdmin(admin));

        verify(adminRepo, times(1)).findById(anyInt());
        verify(adminRepo, never()).save(any(Admin.class));
    }

    @Test
    public void testDeleteAdmin() {
        int adminId = 1;

        when(adminRepo.findById(adminId)).thenReturn(Optional.of(new Admin()));

        String result = adminService.deleteAdmin(adminId);

        assertEquals("Deleted Successfully", result);
        verify(adminRepo, times(1)).findById(adminId);
        verify(adminRepo, times(1)).deleteById(adminId);
    }

    @Test
    void testDeleteAdmin_NonExistingAdmin() {
        // Arrange
        int adminId = 1; 

        when(adminRepo.findById(adminId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoAdminFoundException.class, () -> adminService.deleteAdmin(adminId));

        verify(adminRepo, times(1)).findById(adminId);
        verify(adminRepo, never()).deleteById(anyInt());
    }
    @Test
    public void testGetAdminById() {
        int adminId = 1;
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setEmail("admin@example.com");

        when(adminRepo.findById(adminId)).thenReturn(Optional.of(admin));

        Admin result = adminService.getAdminbyid(adminId);

        assertEquals(admin, result); 
       // verify(adminRepo, times(1)).findById(adminId);
    }

    @Test
    void testGetAdminById_NonExistingAdmin() {
        // Arrange
        int adminId = 1;

        when(adminRepo.findById(adminId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoAdminFoundException.class, () -> adminService.getAdminbyid(adminId));

        verify(adminRepo, times(1)).findById(adminId);
    }

//    @Test
//    public void testViewAllProducts() {
//        List<ProductDto> productList = new ArrayList<>();
//        productList.add(new ProductDto(1, "Product 1", 10.0));
//        productList.add(new ProductDto(2, "Product 2", 20.0));
//
//        ResponseEntity<List<ProductDto>> response = new ResponseEntity<>(productList, HttpStatus.OK);
//        when(restTemplate.exchange(eq("http://localhost:8087/getAllProducts/"), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class))).thenReturn(response);
//
//        List<ProductDto> result = adminService.viewAllProducts();
//
//        assertEquals(productList, result);
//        verify(restTemplate, times(1)).exchange(eq("http://localhost:8087/getAllProducts/"), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class));
//    }
    @Test
    void testViewAllProducts() {
        // Arrange
        List<CustomerDto> customerList = List.of(
                new CustomerDto(1, "Customer 1","cust","mor","12345","9876543210","hi@gmail.com"),
                new CustomerDto(2, "Customer 2","cust","mor","12345","9876543210","hi@gmail.com")
        );
        ResponseEntity<List<CustomerDto>> response = ResponseEntity.ok(customerList);

        URI uri = URI.create("http://localhost:8082/getCustomers/");

        when(restTemplate.exchange(
                ArgumentMatchers.eq(uri),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.<RequestEntity<Void>>isNull(),
                ArgumentMatchers.<ParameterizedTypeReference<List<CustomerDto>>>any()
        )).thenReturn(response);

        // Act
//        List<CustomerDto> result = adminService.viewAllCustomers();
// 
//        // Assert
//        assertEquals(customerList, result);
//        verify(restTemplate, times(1)).exchange(
//                ArgumentMatchers.eq(uri),
//                ArgumentMatchers.eq(HttpMethod.GET),
//                ArgumentMatchers.<RequestEntity<Void>>isNull(),
//                ArgumentMatchers.<ParameterizedTypeReference<List<CustomerDto>>>any()
//        );
    }

    @Test
    void testGetProductById_InvalidProductId() {
        // Arrange
        int productId = 1;

        when(restTemplate.getForEntity(anyString(), eq(ProductDto.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act and Assert
        assertThrows(InvalidProductIdException.class, () -> adminService.getProductById(productId));

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(ProductDto.class));
    }
    @Test
    public void testGetProductById_ValidProductId() {
        int productId = 1;
        ProductDto productDto = new ProductDto(productId, "Product 1", 10.0);

        ResponseEntity<ProductDto> response = new ResponseEntity<>(productDto, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(ProductDto.class))).thenReturn(response);

        ProductDto result = adminService.getProductById(productId);

        assertEquals(productDto, result);
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(ProductDto.class));
    }

    @Test
    void testViewAllCustomers() {
        // Arrange
        List<CustomerDto> customerList = new ArrayList<>();
        customerList.add(new CustomerDto(1, "Customer 1","cust","mor","12345","9876543210","hi@gmail.com"));
        customerList.add(new CustomerDto(2, "Customer 2","cust","mor","12345","9876543210","hi@gmail.com"));

        ResponseEntity<List<CustomerDto>> response = new ResponseEntity<>(customerList, HttpStatus.OK);
        when(restTemplate.exchange(eq("http://localhost:8082/getCustomers/"), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class))).thenReturn(response);

        // Act
//        List<CustomerDto> result = adminService.viewAllCustomers();
//
//        // Assert
//        assertEquals(customerList, result);
//        verify(restTemplate, times(1)).exchange(eq("http://localhost:8082/getCustomers/"), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class));
    }


    @Test
    void testGetCustomerById_InvalidCustomerId() {
        // Arrange
        int customerId = 1;

        when(restTemplate.getForEntity(anyString(), eq(CustomerDto.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act and Assert
        assertThrows(InvalidCustomerIDException.class, () -> adminService.getCustomerById(customerId));

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(CustomerDto.class));
    }

    @Test
    public void testGetCustomerById_ValidCustomerId() {
        int customerId = 1;
        CustomerDto customerDto = new CustomerDto(customerId, "Customer 1","cust","mor","12345","9876543210","hi@gmail.com");

        ResponseEntity<CustomerDto> response = new ResponseEntity<>(customerDto, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(CustomerDto.class))).thenReturn(response);

        CustomerDto result = adminService.getCustomerById(customerId);

        assertEquals(customerDto, result);
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(CustomerDto.class));
    }

    @Test
    void testGetBookingById_InvalidBookingId() {
        // Arrange
        int bookingId = 1;

        when(restTemplate.getForEntity(anyString(), eq(BookingDetailsDto.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act and Assert
        assertThrows(InvalidBookingIdException.class, () -> adminService.getBookingById(bookingId));

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(BookingDetailsDto.class));
    }

    

    @Test
    public void testGetAdminByEmail() {
        String email = "admin@example.com";
        Admin admin = new Admin();
        admin.setId(1);
        admin.setEmail(email);

        when(adminRepo.findByEmail(email)).thenReturn(Optional.of(admin));

        Admin result = adminService.getAdminbyEmail(email);

        assertEquals(admin, result);
        verify(adminRepo, times(1)).findByEmail(email);
    }
    @Test
    void testGetAdminByEmail_NonExistingAdmin() throws NoAdminFoundException {
        // Arrange
        String email = "admin@example.com";
        when(adminRepo.findByEmail(email)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoAdminFoundException.class, () -> adminService.getAdminbyEmail(email));

        verify(adminRepo, times(1)).findByEmail(email);
    }
}
