package org.sysc4806.sysc4806_group20;

import org.springframework.data.repository.CrudRepository;

public interface StudentModelRepositody extends CrudRepository<StudentModel,Long> {

    public StudentModel findByName(String firstName, String lastName);
    public StudentModel findStudentModelByStudentID(Long studentID);

}
