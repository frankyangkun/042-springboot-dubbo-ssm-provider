package com.frank.springboot.mapper;

import com.frank.springboot.model.Student;
import org.springframework.stereotype.Component;

@Component
public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    /**
     * 查询学生总人数
     * @return
     */
    Integer selectAllStudentCount();//方法名必须和DAO映射文件中id相同
}