package io.anymobi.services.mybatis.student.impl;

import io.anymobi.domain.dto.cache.Student;
import io.anymobi.repositories.mybatis.mapper.student.StudentMapper;
import io.anymobi.services.mybatis.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Package : io.anymobi.services.mybatis.student
 * Developer Team : Anymobi System Development Division
 * Date : 2019-03-02
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */
@Repository("studentService")
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student update(Student student) {
        this.studentMapper.update(student);
        return this.studentMapper.queryStudentBySno(student.getSno());
    }

    @Override
    public void deleteStudentBySno(String sno) {
        this.studentMapper.deleteStudentBySno(sno);
    }

    @Override
    public Student queryStudentBySno(String sno) {
        return this.studentMapper.queryStudentBySno(sno);
    }

}
