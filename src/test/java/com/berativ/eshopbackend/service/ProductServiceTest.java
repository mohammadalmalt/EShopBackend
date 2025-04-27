package com.berativ.eshopbackend.service;

import com.berativ.eshopbackend.dto.ProductDTO;
import com.berativ.eshopbackend.model.Product;
import com.berativ.eshopbackend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_withoutFilters_returnsAllProducts() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        List<ProductDTO> result = productService.getAllProducts(null, null, null, null);

        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getName());
        verify(productRepository).findAll();
    }

    @Test
    void createProduct_savesAndReturnsProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("New Product");
        Product product = new Product();
        product.setName("New Product");

        when(modelMapper.map(productDTO, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertEquals("New Product", result.getName());
        verify(productRepository).save(product);
    }
}
