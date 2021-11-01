package com.frank.springboot.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.frank.springboot.mapper.StudentMapper;
import com.frank.springboot.model.Student;
import com.frank.springboot.service.StudentService;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = StudentService.class, version = "1.0.0",timeout = 15000)
@Slf4j
public class StudentServiceImpl implements StudentService {

    //注入持久层
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Student queryStudentById(Integer id) {
        log.debug("服务提供者，DAO接口实现类调用selectByPrimaryKey()debug日志测试。。");
        log.error("服务提供者，DAO接口实现类调用selectByPrimaryKey()error日志测试。。");
        return studentMapper.selectByPrimaryKey(id);
    }

    /**
     * Redis存数据方法实现
     * @param id
     * @param object
     */
    @Override
    public void putRedis(Integer id, Object object) {
        redisTemplate.opsForValue().set(id,object);
    }

    /**
     * 根据id从redis取数据查询学生详情
     * @param id
     * @return
     */
    @Override
    public Student queryStudentByRedis(Integer id) {
        Student student = (Student) redisTemplate.opsForValue().get(id);
        if(null == student){//判断是否有数据，若为空则从mysql查询并存入redis
            student = studentMapper.selectByPrimaryKey(id);
            redisTemplate.opsForValue().set(id,student,30, TimeUnit.SECONDS);
            log.info("服务提供端：根据id查询学生详情，从mysql获取，并存入redis缓存。。。");
        }
        log.info("服务提供端：根据id查询学生详情，从redis缓存获取。。。");
        return student;
    }

    /**
     * 查询学生总人数，先从redis查，如果没有，从mysql查，并存入redis
     * @return
     */
//    @HystrixCommand(commandProperties = { //2021-09-24 16:19:00补充熔断器，测试失败，不用它了
//        @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
//        @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2000")
//    })
    @Override
    public Integer queryStudentCount() {
        Integer allStudentCount = (Integer) redisTemplate.opsForValue().get("allStudentCount");
        if(null == allStudentCount){
            allStudentCount = studentMapper.selectAllStudentCount();
            redisTemplate.opsForValue().set("allStudentCount",allStudentCount,30,TimeUnit.SECONDS);
            log.info("服务提供端：查询学生总人数，并存入缓存redis。。。");
        }
        log.info("服务提供端：查询学生总人数，从缓存redis中获取。。。");
        return allStudentCount;
//        throw new RuntimeException("熔断器异常测试");
    }
}
