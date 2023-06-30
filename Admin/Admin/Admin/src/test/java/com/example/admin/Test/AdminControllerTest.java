package com.example.admin.Test;

	import com.example.admin.Entity.Admin;
import com.example.admin.controller.AdminController;
import com.example.admin.exception.NoAdminFoundException;
	import com.example.admin.modal.BookingDetailsDto;
	import com.example.admin.modal.CustomerDto;
	import com.example.admin.modal.ProductDto;
	import com.example.admin.service.AdminServiceImpl;
	import org.junit.jupiter.api.*;
	import org.mockito.*;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;

	import java.util.List;
	import java.util.Optional;

	import static org.junit.jupiter.api.Assertions.assertEquals;
	import static org.mockito.Mockito.*;

	class AdminControllerTest {

	    @Mock
	    private AdminServiceImpl adminService;

	    @InjectMocks
	    private AdminController adminController;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.initMocks(this);
	    }

	    @Test
	    void testInsertAdmin() {
	        // Arrange
	        Admin admin = new Admin();
	        admin.setId(1);
	        admin.setEmail("admin@example.com");
	        ResponseEntity<Admin> expectedResponse = new ResponseEntity<>(admin, HttpStatus.OK);

	        when(adminService.createAdmin(admin)).thenReturn(admin);

	        // Act
	        ResponseEntity<Admin> response = adminController.insertAdmin(admin);

	        // Assert
	        assertEquals(expectedResponse, response);
	        verify(adminService, times(1)).createAdmin(admin);
	    }

	    // Add more test cases for other methods

	}


