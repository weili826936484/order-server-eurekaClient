package com.weili.order_service.service;

import com.weili.order_service.model.Order;
import com.weili.order_service.model.Product;

public interface OrderService {

    Order saveOrder(Integer userId, Integer productId);

    Product savePruduct();

    Product savePruduct2();


    Order saveOrder2(Integer userId, Integer productId);
}
