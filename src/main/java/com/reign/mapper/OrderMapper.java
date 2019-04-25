package com.reign.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderMapper
 * @Description mapper类
 * @Author wuwenxiang
 * @Date 2019-04-25 21:39
 * @Version 1.0
 **/
@Repository
public interface OrderMapper {

    @Select("select * from batch_insert where orderId like '%${orderId}%'")
    List<Map<String,Object>> queryList(@Param("orderId") String orderId);

    //redis预热，先从数据库中查询出一万条数据；
    @Select("select * from batch_insert limit 0,10000")
    List<Map<String,Object>> queryRedis();
}
