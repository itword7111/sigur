package com.example.sigur.component;

import com.example.sigur.entity.Department;
import com.example.sigur.entity.Employee;
import com.example.sigur.entity.Guest;
import com.example.sigur.repository.DepartmentRepository;
import com.example.sigur.repository.EmployeeRepository;
import com.example.sigur.repository.GuestRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;

@Component
public class EmployeesMgr {
    @Autowired
    VirtualData virtualData;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    GuestRepository guestRepository;

    private Logger logger = LoggerFactory.getLogger(EmployeesMgr.class);

    private Date endDate = new Calendar.Builder()
            .setDate(2022,Calendar.DECEMBER,31)
            .build()
            .getTime();
    private int count;

    @Scheduled(fixedDelay = 1000)
    private void scheduledHiring(){
        count++;
        if (virtualData.getDate().getTime()<=endDate.getTime()){
            byte[] card=generateCard();
            Employee employee=new Employee(card, new Date(
                    virtualData.getDate().getTime() + (long) (Math.random() * (endDate.getTime() - virtualData.getDate().getTime()))));
            employee.setDepartment(departmentRepository.getAllDepartments()
                    .get(new Random().nextInt(10)));
            employeeRepository.addPerson(employee);

            logger.info("{Дата текущего дня, отсчитываемого компонентом ("+virtualData.getDate()+")}. Сотрудник \n" +
                    "{"+employee.getId()+"} нанят {дата и время найма ("+employee.getHireTime()+")}. Отдел: {"+employee.getDepartment().getName()+"}");

            if (new Random().nextBoolean()){
                Calendar calendar=new Calendar.Builder().setInstant(employee.getHireTime()).build();
                calendar.add(Calendar.MONTH,6);
                Guest guest = new Guest(generateCard(),new Date(
                        employee.getHireTime().getTime()
                                + (long) (Math.random()
                                * (calendar.getTime().getTime()
                                - employee.getHireTime().getTime()))),employee);
                guestRepository.addGuest(guest);

                logger.info("Гостю ${"+guest.getId()+"} назначена встреча сотруднику ${Идентификатор \n" +
                        "сотрудника. Отдел: {"+employee.getDepartment().getName()+"}. Дата: {дата встречи ("+guest.getVisitDate()+")}. До \n" +
                        "встречи осталось: ${число дней ("+ ((guest.getVisitDate().getTime()-virtualData.getDate().getTime())/86400000L)+")}");

            }

            if(count==5){
                count=0;
                List<Employee> employeeList = employeeRepository.getAllEmployeesWithNoFiredTime();
                for(int i=0;i<=new Random().nextInt(2)&&employeeList.size()>0;i++){
                    int index = new Random().nextInt(employeeList.size());
                    Date firedTime=new Date(employeeList.get(index).getHireTime().getTime() + (long) (Math.random()
                            * (endDate.getTime() - employeeList.get(index).getHireTime().getTime())));
                    employeeRepository.editEmployee(firedTime, employeeList.get(index).getId());

                    logger.info("{Дата текущего дня, отсчитываемого компонентом ("+virtualData.getDate()+")}. Сотрудник \n" +
                            "{"+employee.getId()+"} уволен {дата и время увольнения ("+firedTime+")}. Отдел: \n" +
                            "{"+employee.getDepartment().getName()+"}. Проработал: {количество дней, проведённых в штате ("+((firedTime.getTime()-employee.getHireTime().getTime())/86400000L)+")}");

                }
            }
        }
    }
    private byte[] generateCardCode(){
        byte[] bytes=new byte[16];
        Random random=new Random();
        for(int i=0;i<16;i++){
            bytes[i]= (byte) random.nextInt(127);
        }
        return bytes;
    }
    private byte[] generateCard(){
        byte[] card=this.generateCardCode();
        while (employeeRepository.isCardCodeExists(card)||guestRepository.isCardCodeExists(card)){
            card=this.generateCardCode();
        }
        return card;
    }
}
