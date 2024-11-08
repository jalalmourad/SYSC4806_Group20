package org.sysc4806.sysc4806_group20;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentModelRepository extends CrudRepository<StudentModel,Long> {

    public StudentModel findStudentModelByStudentID(Long studentID);

}
