package com.weili.order_service.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.weili.order_service.model.Order;
import com.weili.order_service.model.Product;
import com.weili.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/saveOrder/v1")
    public Order saveOrder(@RequestParam("userId") Integer userId , @RequestParam("productId") Integer productId){
         return orderService.saveOrder(userId,productId);
    }

    @RequestMapping(value = "/savePruduct/v1")
    public Product savePruduct(){
        return orderService.savePruduct();
    }

    @RequestMapping(value = "/savePruduct/v2")
    public Product savePruduct2(){
        return orderService.savePruduct2();
    }

    /**
     * feign
     * @param userId
     * @param productId
     * @return
     */
    @RequestMapping(value = "/saveOrder/v2")
    //@HystrixCommand(fallbackMethod = "saveOrderError")
    public Order saveOrder2(@RequestParam("userId") Integer userId , @RequestParam("productId") Integer productId){
        return orderService.saveOrder2(userId,productId);
    }

    public Order saveOrderError(Integer userId ,Integer productId){
        Order order = new Order();
        order.setReturnCode(101);
        order.setMsg("服务出错 ！！");
        return order;
    }
}
