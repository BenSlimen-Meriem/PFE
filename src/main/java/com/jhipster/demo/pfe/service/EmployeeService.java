package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.Employee;
import com.jhipster.demo.pfe.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.Employee}.
 */
@Service
@Transactional
public class EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public Employee save(Employee employee) {
        LOG.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Update a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public Employee update(Employee employee) {
        LOG.debug("Request to update Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Partially update a employee.
     *
     * @param employee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Employee> partialUpdate(Employee employee) {
        LOG.debug("Request to partially update Employee : {}", employee);

        return employeeRepository
            .findById(employee.getId())
            .map(existingEmployee -> {
                if (employee.getNom() != null) {
                    existingEmployee.setNom(employee.getNom());
                }
                if (employee.getQualification() != null) {
                    existingEmployee.setQualification(employee.getQualification());
                }

                return existingEmployee;
            })
            .map(employeeRepository::save);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        LOG.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable);
    }

    /**
     *  Get all the employees where Executeur is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Employee> findAllWhereExecuteurIsNull() {
        LOG.debug("Request to get all employees where Executeur is null");
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
            .filter(employee -> employee.getExecuteur() == null)
            .toList();
    }

    /**
     *  Get all the employees where Planificateur is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Employee> findAllWherePlanificateurIsNull() {
        LOG.debug("Request to get all employees where Planificateur is null");
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
            .filter(employee -> employee.getPlanificateur() == null)
            .toList();
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Employee> findOne(Long id) {
        LOG.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
