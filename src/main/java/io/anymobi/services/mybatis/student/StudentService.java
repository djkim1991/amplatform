package io.anymobi.services.mybatis.student;

import io.anymobi.domain.dto.cache.Student;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * Package : io.anymobi.services.mybatis.student
 * Developer Team : Anymobi System Development Division
 * Date : 2019-03-02
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */
@CacheConfig(cacheNames = "student")
public interface StudentService {

    @CachePut(key = "#p0.sno")
    Student update(Student student);

    @CacheEvict(key = "#p0", allEntries = true)
    void deleteStudentBySno(String sno);

    @Cacheable(key = "#p0")
    Student queryStudentBySno(String sno);
}
