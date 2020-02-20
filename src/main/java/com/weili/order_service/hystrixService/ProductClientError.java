package com.weili.order_service.hystrixService;

import ch.qos.logback.core.util.TimeUtil;
import com.weili.order_service.client.ProductClient;
import com.weili.order_service.model.Order;
import com.weili.order_service.model.Product;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Component
public class ProductClientError implements ProductClient {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String clientName = "product-server";
    @Override
    public Product getProduntById(Integer id) {
        Product product = new Product();
        product.setReturnCode(101);
        //开启线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送短信
                String result = stringRedisTemplate.opsForValue().get(clientName);
                if (StringUtils.isBlank(result)){
                    stringRedisTemplate.opsForValue().set(clientName,"product-server-error",10, TimeUnit.SECONDS);
                    System.out.println("紧急通知。。。服务器宕机啦..");
                }else {
                    System.out.println("10秒内不能重复发信息....");
                }
            }
        }).start();
        product.setMsg("服务出错 ！！");
        System.out.println("商品服务器宕机啦。。。。");
        return product;
    }
}
