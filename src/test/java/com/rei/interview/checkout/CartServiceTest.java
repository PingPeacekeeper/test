package com.rei.interview.checkout;

import com.rei.interview.inventory.InventoryService;
import com.rei.interview.product.Product;
import com.rei.interview.product.ProductService;
import com.rei.interview.rs.Location;
import junit.framework.TestCase;
import org.junit.Before;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class CartServiceTest {


    @Mock
    private InventoryService inventoryService;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductService productService;

    private CartService service;

    private Product product;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new Product();
        product.setProductId("123456");
        product.setBrand("brand");
        product.setPrice(new BigDecimal(10));
        product.setDescription("description");

        service = new CartService(productService, inventoryService, cartRepository);

    }


    @Test
    public void testInvalidProduct() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("Invalid Product");
        product.setDescription(null);
        service.addToCart("1", product, 1, Location.ASHEVILLE);

        exception.expect(Exception.class);
        exception.expectMessage("Invalid Product");
        product.setDescription("description");
        product.setProductId("12345");
        service.addToCart("1", product, 1, Location.ASHEVILLE);

        // junit 5
//        product.setProductId("123456");
//        product.setPrice(new BigDecimal(0));
//        exception = Assertions.assertThrows(Exception.class, () -> {
//            service.addToCart("1", product, 1, Location.ASHEVILLE);
//        });
//        assertEquals("Invalid Product", exception.getMessage());

    }

    @Test
    public void testInvalidInventory() throws Exception {


        exception.expect(Exception.class);
        exception.expectMessage("No inventory for this product");
        Mockito.when(inventoryService.hasInventoryOnline(product, 1)).thenReturn(false);
        Mockito.when(cartRepository.getCart("1")).thenReturn(null);
        service.addToCart("1", product, 1, Location.ASHEVILLE);


//        Mockito.when(inventoryService.hasInventoryOnline(product, 1)).thenReturn(true);
//        Mockito.when(inventoryService.hasInventoryInNearbyStores(product, 1, Location.ASHEVILLE)).thenReturn(false);
//        exception = Assertions.assertThrows(Exception.class, () -> {
//            service.addToCart("1", product, 1, Location.ASHEVILLE);
//        });
//        assertEquals("No inventory for this product", exception.getMessage());
    }
}