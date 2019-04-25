package com.reign;

import com.reign.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName OrderServer
 * @Description TODO  问题：在redis中放全部数据还是只放订单id
 * @Author wuwenxiang
 * @Date 2019-04-25 21:43
 * @Version 1.0
 **/
@Component
public class OrderServer {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    JedisPool jedisPool;

    public void queryList(String orderId){
        long start = System.currentTimeMillis();
        orderMapper.queryList(orderId);
        long end = System.currentTimeMillis();
        System.out.println("查询耗时："+(end-start)/1000 +"秒");
    }

    /**
     * 对redis进行预热；预热一次完成，完成后该方法就不需要调用了
     */
//    @PostConstruct
//    public void init(){
//        Jedis jedis = jedisPool.getResource();
//        //从数据库中查询1w条数据用于预热；
//        List<Map<String,Object>> data = orderMapper.queryRedis();
//        //是将所有数据一次性放入redis还是一条条的放入；
//        //如果将所有数据一次性放入，那取出来的时候也是所有都拿出，所以是一条条的放入；
//        for(Map<String,Object> itemMap:data){
//            jedis.set(itemMap.get("orderId").toString(),itemMap.get("id").toString());
//        }
//        //看看是否已经全部预热了；
//        System.out.println(jedis.keys("*").size());
//    }

    public void queryRedis(String orderId){
        Jedis jedis = jedisPool.getResource();
        long start = System.currentTimeMillis();
        Set<String> keys =jedis.keys("*"+orderId+"*");
        System.out.println("redis中查到的数据："+keys.size());
        long end = System.currentTimeMillis();
        System.out.println("从redis中查询耗时："+(end-start)/1000);
    }

}
