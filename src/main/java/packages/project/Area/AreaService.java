package packages.project.Area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packages.project.Fee.Fee;

import java.util.List;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    @Autowired
    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }


    public List<Area> getAllAreas(){
        return areaRepository.findAll();
    }

   public Area getArea(Integer areaId){
        return areaRepository.getAreaByAreaId(areaId);
   }

}
