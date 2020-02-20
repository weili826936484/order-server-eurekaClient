package com.weili.order_service.service.impl;

import com.netflix.discovery.converters.Auto;
import com.weili.order_service.client.ProductClient;
import com.weili.order_service.model.Order;
import com.weili.order_service.model.Product;
import com.weili.order_service.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProductClient productClient;
    //redis引擎
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);



    @Override
    public Order saveOrder2(Integer userId, Integer productId) {
        /*System.out.println(1/0);*/
        //获取商品信息
        Product product =  productClient.getProduntById(productId);
        logger.info("getProduntById");
        //封装订单信息
        Order order = new Order();
        order.setDate(new Date());
        order.setUserId(userId);
        order.setUserName("魏立");
        order.setOrderCode(String.valueOf(Math.random()));
        order.setProductName(product.getName());
        order.setPrice(product.getPrice());
        order.setPort(product.getPort());
        return order;
    }

    @Override
    public Product savePruduct2() {
        Product product2 = new Product();
        product2.setId(4);
        product2.setName("iphone13");
        product2.setCode("0004");
        product2.setPrice(new BigDecimal(1000000));
        product2.setDesc("神经网络cpu A14");
        //模拟发送post请求 @ResponseBody接收参数
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity("http://PRODUCT-SERVER/api/product/saveProduct/v1",product2, Map.class);
        Map<String,Object> map = responseEntity.getBody();
        if (Integer.valueOf(map.get("code").toString()) == 200){
            System.out.println(map.toString());
        }
        //获取刚刚插入的商品
        ServiceInstance instance = loadBalancerClient.choose("PRODUCT-SERVER");
        String url = String.format("http://%s:%s/api/product/findById/v1/4",instance.getHost(),instance.getPort());
        RestTemplate template = new RestTemplate();
        Product product =  template.getForObject(url,Product.class);
        return product;
    }

    @Override
    public Product savePruduct() {
        Product product2 = new Product();
        product2.setId(3);
        product2.setName("iphone12");
        product2.setCode("0003");
        product2.setPrice(new BigDecimal(100000));
        product2.setDesc("神经网络cpu A13");
        //模拟发送post请求 @ResponseBody接收参数
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity("http://PRODUCT-SERVER/api/product/saveProduct/v1",product2, Map.class);
        Map<String,Object> map = responseEntity.getBody();
        if (Integer.valueOf(map.get("code").toString()) == 200){
            System.out.println(map.toString());
        }
        //获取刚刚插入的商品
        ServiceInstance instance = loadBalancerClient.choose("PRODUCT-SERVER");
        String url = String.format("http://%s:%s/api/product/findById/v1/3",instance.getHost(),instance.getPort());
        RestTemplate template = new RestTemplate();
        Product product =  template.getForObject(url,Product.class);
        return product;
    }

    @Override
    public Order saveOrder(Integer userId, Integer productId) {
        //获取商品信息
        //Product product = restTemplate.getForObject("http://PRODUCT-SERVER/api/product/findById/v1/1", Product.class);
        ServiceInstance instance = loadBalancerClient.choose("PRODUCT-SERVER");
        String url = String.format("http://%s:%s/api/product/findById/v1/1",instance.getHost(),instance.getPort());
        RestTemplate template = new RestTemplate();
        Product product =  template.getForObject(url,Product.class);
        //封装订单信息
        Order order = new Order();
        order.setDate(new Date());
        order.setUserId(userId);
        order.setUserName("魏立");
        order.setOrderCode(String.valueOf(Math.random()));
        order.setProductName(product.getName());
        order.setPrice(product.getPrice());
        order.setPort(product.getPort());
        return order;
    }
}
