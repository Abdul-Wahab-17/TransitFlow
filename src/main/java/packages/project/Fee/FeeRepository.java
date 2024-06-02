package packages.project.Fee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository <Fee , Integer>{

    @Query("SELECT F from Fee F WHERE F.area.areaId = :areaId")
    public List<Fee> getFeeFromAreaId(Integer areaId);



}
