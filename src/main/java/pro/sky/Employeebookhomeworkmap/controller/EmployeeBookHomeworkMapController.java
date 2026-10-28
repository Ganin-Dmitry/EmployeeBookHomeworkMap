package pro.sky.Employeebookhomeworkmap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.Employeebookhomeworkmap.exception.EmployeeAlreadyAddedException;
import pro.sky.Employeebookhomeworkmap.exception.EmployeeNotFoundException;
import pro.sky.Employeebookhomeworkmap.exception.EmployeeStorageIsFullException;
import pro.sky.Employeebookhomeworkmap.service.EmployeeBookHomeworkMapService;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeBookHomeworkMapController {
    private final EmployeeBookHomeworkMapService service;
    public EmployeeBookHomeworkMapController (EmployeeBookHomeworkMapService service) {
        this.service = service;
    }

    @GetMapping(path = "/add")
    public String add (@RequestParam (required = false) String firstName,
                       @RequestParam (required = false) String middleName,
                       @RequestParam (required = false) String lastName,
                       @RequestParam (required = false) Integer salary,
                       @RequestParam (required = false) Integer department) {
        if (firstName.isBlank() || middleName.isBlank() || lastName.isBlank() || salary == null || department == null) {
            return "Допущена ошибка во вводе аргументов!";
        }
        try {
            return service.addEmployee(firstName, middleName, lastName, salary, department);
        } catch (EmployeeAlreadyAddedException e) {
            return "Данный сотрудник уже добавлен!";
        } catch (EmployeeStorageIsFullException e) {
            return "Достигнут лимит сотрудников в хранилище!";
        } catch (Exception e) {
            return "Попробуйте позже.";
        }
    }

    @GetMapping(path = "/remove")
    public String remove (@RequestParam (required = false) String fullName) {
        if (fullName.isBlank()) {
            return "Не введено поле аргумента!";
        }
        try {
            return service.removeEmployee(fullName);
        } catch (EmployeeNotFoundException e) {
            return "Сотрудник не найден!";
        } catch (Exception e) {
            return "Попробуйте позже.";
        }
    }
    @GetMapping(path = "/find")
    public String find (@RequestParam (required = false) String fullName) {
        if (fullName.isBlank()) {
            return "Не введено поле аргумента!";
        }
        try {
            return service.findEmployee(fullName);
        } catch (EmployeeNotFoundException e) {
            return "Сотрудник не найден!";
        } catch (Exception e) {
            return "Попробуйте позже.";
        }
    }
    @GetMapping
    public String printEmployeeList () {
        try {
            return service.getEmployeeList();
        } catch (EmployeeNotFoundException e) {
            return "Сотрудников в хранилище не найдено.";
        }
    }
}
