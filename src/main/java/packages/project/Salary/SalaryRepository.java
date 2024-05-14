package packages.project.Salary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository <Salary , Integer> {

@Query("select S from Salary S where S.area.areaId = :areaId")
public Salary getSalaryFromArea(Integer areaId);

}
