package packages.project.Fee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeService {

    private final FeeRepository feeRepository;

    @Autowired
    public FeeService(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    public List<Fee> getFee(Integer areaId){
        return feeRepository.getFeeFromAreaId(areaId);
    }

    public List<Fee> getAllFees(){
        return feeRepository.findAll();
    }

    public Fee getFeeById(Integer feeId){
        return feeRepository.getById(feeId);
    }
}
