package org.sysc4806.sysc4806_group20.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.sysc4806.sysc4806_group20.Model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

}
