package com.weili.order_service.client;

import com.weili.order_service.hystrixService.ProductClientError;
import com.weili.order_service.model.Product;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "PRODUCT-SERVER",fallback = ProductClientError.class)
public interface ProductClient {
    @GetMapping("api/product/findById/v1/{id}")
    public Product getProduntById(@PathVariable(value = "id") Integer id);
}
