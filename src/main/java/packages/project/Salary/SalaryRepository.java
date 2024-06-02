package packages.project.Salary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository <Salary , Integer> {

@Query("select S from Salary S where S.area.areaId = :areaId")
public List<Salary> getSalaryFromArea(Integer areaId);

}
