package com.ems.EmployeeManagementSystem.service.adminService;

import com.ems.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.ems.EmployeeManagementSystem.dto.EmployeeResponseDTO;
import com.ems.EmployeeManagementSystem.exceptionHandling.EmployeeNotFoundException;
import com.ems.EmployeeManagementSystem.interfaces.AdminEmployeeServiceIF;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.LeaveSummary;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepo;
import com.ems.EmployeeManagementSystem.repository.LeaveSummaryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEmployeeService implements AdminEmployeeServiceIF {

    private final EmployeeRepo employeeRepo;
    private final EmployeeResponseDTO employeeResponseDTO;
    private final LeaveSummary leaveSummary;
    private final LeaveSummaryRepo leaveSummaryRepo;

    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees() {
        List<Employee> emp = employeeRepo.findAll();
        return employeeResponseDTO.employeeResponse(emp);
    }

    public ResponseEntity<Employee> addEmployee(EmployeeRequestDTO employeeRequestDTO){

        Employee emp = employeeRequestDTO.employeeRequest(employeeRequestDTO);

        employeeRepo.save(emp);

        leaveSummary.setMonth(YearMonth.now().getMonth().getValue());
        leaveSummary.setYear(YearMonth.now().getYear());
        leaveSummary.setEmployee(emp);

        leaveSummaryRepo.save(leaveSummary);

        return new ResponseEntity<>(emp, HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateEmployee(long id, EmployeeRequestDTO employee) {

        Employee emp = employeeRepo.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException("Employee Not Found for id : "+id));

            emp.setName(employee.getName());
            emp.setEmail(employee.getEmail());
            emp.setPhone(employee.getPhone());
            emp.setUserName(employee.getUserName());
            emp.setRole(employee.getRole());
            emp.setAddress(employee.getAddress());
            emp.setDepartment(employee.getDepartment());
            emp.setBloodGroup(employee.getBloodGroup());
            emp.setCity(employee.getCity());
            emp.setState(employee.getState());
            emp.setDob(employee.getDob());
            emp.setGender(employee.getGender());
            emp.setFatherName(employee.getFatherName());
            emp.setJoinDate(employee.getJoinDate());
            emp.setNationality(employee.getNationality());
            emp.setMaritalStatus(employee.getMaritalStatus());
            emp.setSpouseName(employee.getSpouseName());
            emp.getProfilePic().setImageName(employee.getImageName());
            emp.getProfilePic().setImageType(employee.getImageType());
            emp.getProfilePic().setImageData(employee.getImageData());
            emp.setWorkLocation(employee.getWorkLocation());

            employeeRepo.save(emp);
            return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteEmployee(long id) {

        employeeRepo.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException("Employee Not Found for id : "+id));

            employeeRepo.deleteById(id);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }
}
