package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.DepartementTestSamples.*;
import static com.jhipster.demo.pfe.domain.EmployeeTestSamples.*;
import static com.jhipster.demo.pfe.domain.ExecuteurTestSamples.*;
import static com.jhipster.demo.pfe.domain.PlanificateurTestSamples.*;
import static com.jhipster.demo.pfe.domain.SocieteTestSamples.*;
import static com.jhipster.demo.pfe.domain.WorkOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = getEmployeeSample1();
        Employee employee2 = new Employee();
        assertThat(employee1).isNotEqualTo(employee2);

        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);

        employee2 = getEmployeeSample2();
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void societeTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Societe societeBack = getSocieteRandomSampleGenerator();

        employee.setSociete(societeBack);
        assertThat(employee.getSociete()).isEqualTo(societeBack);

        employee.societe(null);
        assertThat(employee.getSociete()).isNull();
    }

    @Test
    void departementTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Departement departementBack = getDepartementRandomSampleGenerator();

        employee.setDepartement(departementBack);
        assertThat(employee.getDepartement()).isEqualTo(departementBack);

        employee.departement(null);
        assertThat(employee.getDepartement()).isNull();
    }

    @Test
    void workOrderTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        WorkOrder workOrderBack = getWorkOrderRandomSampleGenerator();

        employee.addWorkOrder(workOrderBack);
        assertThat(employee.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getEmployees()).containsOnly(employee);

        employee.removeWorkOrder(workOrderBack);
        assertThat(employee.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getEmployees()).doesNotContain(employee);

        employee.workOrders(new HashSet<>(Set.of(workOrderBack)));
        assertThat(employee.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getEmployees()).containsOnly(employee);

        employee.setWorkOrders(new HashSet<>());
        assertThat(employee.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getEmployees()).doesNotContain(employee);
    }

    @Test
    void executeurTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Executeur executeurBack = getExecuteurRandomSampleGenerator();

        employee.setExecuteur(executeurBack);
        assertThat(employee.getExecuteur()).isEqualTo(executeurBack);
        assertThat(executeurBack.getEmployee()).isEqualTo(employee);

        employee.executeur(null);
        assertThat(employee.getExecuteur()).isNull();
        assertThat(executeurBack.getEmployee()).isNull();
    }

    @Test
    void planificateurTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Planificateur planificateurBack = getPlanificateurRandomSampleGenerator();

        employee.setPlanificateur(planificateurBack);
        assertThat(employee.getPlanificateur()).isEqualTo(planificateurBack);
        assertThat(planificateurBack.getEmployee()).isEqualTo(employee);

        employee.planificateur(null);
        assertThat(employee.getPlanificateur()).isNull();
        assertThat(planificateurBack.getEmployee()).isNull();
    }
}
