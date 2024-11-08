package org.sysc4806.sysc4806_group20.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.sysc4806.sysc4806_group20.Model.Professor;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long> {
}
