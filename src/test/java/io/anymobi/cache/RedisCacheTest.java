package io.anymobi.cache;

import io.anymobi.domain.dto.cache.Student;
import io.anymobi.services.mybatis.student.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Package : io.anymobi.cache
 * Developer Team : Anymobi System Development Division
 * Date : 2019-03-02
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void test1() {
        Student student1 = this.studentService.queryStudentBySno("001");
        System.out.println("학번" + student1.getSno() + "이름：" + student1.getName());

        Student student2 = this.studentService.queryStudentBySno("001");
        System.out.println("학번" + student2.getSno() + "이름：" + student2.getName());
    }

    @Test
    public void test2() {
        Student student1 = this.studentService.queryStudentBySno("001");
        System.out.println("학번" + student1.getSno() + "이름：" + student1.getName());

        student1.setName("anymobi");
        this.studentService.update(student1);

        Student student2 = this.studentService.queryStudentBySno("001");
        System.out.println("학번" + student2.getSno() + "이름：" + student2.getName());
    }
}
