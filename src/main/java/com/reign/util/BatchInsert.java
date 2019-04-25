package com.reign.util;

/**
 * @ClassName BatchInsert
 * @Description 批量插入测试数据
 * @Author wuwenxiang
 * @Date 2019-04-25 21:00
 * @Version 1.0
 **/

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

@RequestMapping(value = "mysql-test")
@RestController
public class BatchInsert {
        public static void main(String[] args) {
            try {
                Connection conn = null;
                Class.forName("com.mysql.jdbc.Driver").newInstance(); // MYSQL驱动
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bookstore?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8",
                        "root", "123456"); // 链接本地MYSQL
                int batchSize = 50000;
                int count = 5000000;
                conn.setAutoCommit(false);//设置自动提交为false
                PreparedStatement ps = conn.prepareStatement("insert into batch_insert (id,orderId) values (?,?)");
                Long t1 = System.currentTimeMillis();
                System.out.println("====================");
                for (long i = 1; i < count; i++) {
                    ps.setLong(1, i);//设置第一个参数的值为i
                    ps.setString(2, UUID.randomUUID().toString());
                    ps.addBatch();//将该条记录添加到批处理中
                    if (i % batchSize == 0) {
                        ps.executeBatch();//执行批处理
                        conn.commit();//提交
                        System.out.println(i + ":添加" + batchSize + "条");
                    }
                }
                if ((count + 1) % batchSize != 0) {
                    ps.executeBatch();
                    conn.commit();
                }
                ps.close();
                Long t2 = System.currentTimeMillis();
                System.out.println(count + "条    每次" + batchSize + "条   " + (t2 - t1) / 1000 + "秒");
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

