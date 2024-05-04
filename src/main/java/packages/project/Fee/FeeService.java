package packages.project.Fee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {

    private final FeeRepository feeRepository;

    @Autowired
    public FeeService(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    public Fee getFee( Integer areaId){
        return feeRepository.getFeeFromAreaId(areaId);
    }
}
