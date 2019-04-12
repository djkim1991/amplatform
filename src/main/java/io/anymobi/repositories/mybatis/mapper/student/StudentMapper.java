package io.anymobi.repositories.mybatis.mapper.student;

import io.anymobi.domain.dto.cache.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;

/**
 * Package : io.anymobi.repositories.mybatis.mapper.student
 * Developer Team : Anymobi System Development Division
 * Date : 2019-03-02
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */
@Mapper
@CacheConfig(cacheNames = "student")
public interface StudentMapper {

    @Update("update student set sname=#{name},ssex=#{sex} where sno=#{sno}")
    int update(Student student);

    @Delete("delete from student where sno=#{sno}")
    void deleteStudentBySno(String sno);

    @Select("select * from student where sno=#{sno}")
    @Results(id = "student", value = { @Result(property = "sno", column = "sno", javaType = String.class),
            @Result(property = "name", column = "sname", javaType = String.class),
            @Result(property = "sex", column = "ssex", javaType = String.class) })
    Student queryStudentBySno(String sno);
}
