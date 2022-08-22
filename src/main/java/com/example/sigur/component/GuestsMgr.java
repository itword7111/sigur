package com.example.sigur.component;

import com.example.sigur.entity.Employee;
import com.example.sigur.entity.Guest;
import com.example.sigur.repository.EmployeeRepository;
import com.example.sigur.repository.GuestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GuestsMgr {
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    private Logger logger = LoggerFactory.getLogger(Guest.class);

    @Scheduled(fixedDelay = 1000)
    private void scheduledVisitCheck(){
        List<Employee> employeeList= employeeRepository.getAllEmployeesWithFiredTime();
        for (Employee employee:employeeList) {
            Guest guest=guestRepository.getGuestByEmployee(employee);
            if(guest!=null&&guest.getVisitDate()!=null&&guest.getVisitDate().compareTo(employee.getFiredTime())>0){
                guestRepository.removeEmployeesVisitDate(guest.getId());
                logger.info("Встреча гостя ${"+guest.getId()+"} с сотрудником ${"+guest.getEmployee().getId()+"} \n" +
                        "отменена. Отдел: {"+guest.getEmployee().getDepartment().getName()+"}. Дата встречи: ${дата встречи ("+guest.getVisitDate()+")}, \n" +
                        "дата увольнения сотрудника: ${"+guest.getEmployee().getFiredTime()+")}");
            }
        }
    }
}
