package packages.project.Area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import packages.project.Fee.Fee;

@Repository
public interface AreaRepository extends JpaRepository <Area , Integer>  {

    @Query("select A from Area A where A.areaId = :areaId")
    public Area getAreaByAreaId(Integer areaId);

}
