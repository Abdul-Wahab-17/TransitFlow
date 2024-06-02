package packages.project.Salary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    private final SalaryRepository salaryRepository;

    @Autowired
    public SalaryService(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    public List<Salary> getSalaryForArea(Integer areaId){
        return salaryRepository.getSalaryFromArea(areaId);
    }

    public List<Salary> getAllSalaries(){
        return  salaryRepository.findAll();
    }

    public Salary getSalaryById(int salaryId) {
        return salaryRepository.getById(salaryId);
    }
}
