package packages.project.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule getCustomerSchedule(Integer customerid){
       return  scheduleRepository.getScheduleByCustomerId(customerid);
    }

    public void save(Schedule schedule ){
        scheduleRepository.save(schedule);
    }

}
