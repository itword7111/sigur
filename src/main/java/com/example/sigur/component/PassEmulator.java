package com.example.sigur.component;

import com.example.sigur.entity.Employee;
import com.example.sigur.entity.Guest;
import com.example.sigur.entity.Person;
import com.example.sigur.model.PersonType;
import com.example.sigur.repository.EmployeeRepository;
import com.example.sigur.repository.GuestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PassEmulator {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    VirtualData virtualData;

    private Logger logger = LoggerFactory.getLogger(PassEmulator.class);

    @Scheduled(fixedDelay = 100)
    private void scheduledVisitCheck(){
        if(new Random().nextInt(5)==0){
            byte [] card=generateCardCode();
            Employee employee=employeeRepository.getEmployeeByCard(card);
            Guest guest=guestRepository.getGuestByCard(card);
            if(employee!=null){
                logForEmployee(employee);
            }
            else if(guest!=null){
                logForGuest(guest);
            }
            else {
                employee=new Employee();
                employee.setCard(card);
                logger.info("Поднесена неизвестная карта: \n" +
                        "{"+ employee.getCardLikeHexString() +"}");
            }
        }
        else {
            List<Person> personList=new ArrayList<>();
            personList.addAll(employeeRepository.getAllEmployees());
            personList.addAll(guestRepository.getAllGuests());
            Person person=personList.get(new Random().nextInt(personList.size()));
            if (person.getType()== PersonType.EMPLOYEE){
                Employee employee =(Employee)person;
                logForEmployee(employee);
            }
            else {
                Guest guest=(Guest)person;
                logForGuest(guest);
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
    private void logForEmployee(Employee employee){
        if(employee.getFiredTime()==null||employee.getFiredTime().getTime() > virtualData.getDate().getTime()){

            logger.info("{"+virtualData.getDate()+"}\n" +
                    "Предоставлен доступ сотруднику {"+employee.getId()+"}. Отдел: {"+employee.getDepartment().getName()+"}. \n" +
                    "Карта: {"+ employee.getCardLikeHexString() +"}");

        }
        else {

            logger.info("{"+virtualData.getDate()+"} Доступ запрещен\n" +
                    "сотруднику {"+employee.getId()+"}. Отдел: {"+employee.getDepartment().getName()+"}. Карта: {"+ employee.getCardLikeHexString() +"}");

        }
    }
    private void logForGuest(Guest guest){
        if(guest.getVisitDate().getDate()==virtualData.getDate().getDate()
                &&guest.getVisitDate().getMonth()==virtualData.getDate().getMonth()
        &&guest.getVisitDate().getYear()==virtualData.getDate().getYear()){

            logger.info("{"+virtualData.getDate()+"} Предоставлен доступ гостю {"+guest.getId()+"}. \n" +
                    "Пришёл к {"+guest.getEmployee().getId()+"} из отдела: {"+guest.getEmployee().getDepartment().getName()+"}. Карта: {"+ guest.getCardLikeHexString() +"}");

        }
        else {

            logger.info("{"+virtualData.getDate()+"} Доступ запрещён гостю {"+guest.getId()+"}.\n" +
                    "Карта: {"+ guest.getCardLikeHexString() +"}");

        }
    }

}
