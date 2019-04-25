package test;

import com.reign.OrderServer;
import com.reign.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName Test
 * @Description TODO
 * @Author wuwenxiang
 * @Date 2019-04-25 21:44
 * @Version 1.0
 **/
public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderServer orderServer = context.getBean(OrderServer.class);
        orderServer.queryList("57c4e-94c4-48b0-aa6c-a933b");
        orderServer.queryRedis("57c4e-94c4-48b0-aa6c-a933b");
        //缓存穿透，如果查询的数据在redis中没有，那么就会直接到数据库中查询；同样该数据在数据库中也没有
        //需要将这种请求直接拦截在redis层；
        //假数据请求同样也会消耗数据库性能

    }

}
