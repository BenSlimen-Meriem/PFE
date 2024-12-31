import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { PlanificateurService } from '../service/planificateur.service';
import { IPlanificateur } from '../planificateur.model';
import { PlanificateurFormService } from './planificateur-form.service';

import { PlanificateurUpdateComponent } from './planificateur-update.component';

describe('Planificateur Management Update Component', () => {
  let comp: PlanificateurUpdateComponent;
  let fixture: ComponentFixture<PlanificateurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let planificateurFormService: PlanificateurFormService;
  let planificateurService: PlanificateurService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PlanificateurUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PlanificateurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlanificateurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    planificateurFormService = TestBed.inject(PlanificateurFormService);
    planificateurService = TestBed.inject(PlanificateurService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call employee query and add missing value', () => {
      const planificateur: IPlanificateur = { id: 1147 };
      const employee: IEmployee = { id: 1749 };
      planificateur.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 1749 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const expectedCollection: IEmployee[] = [employee, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planificateur });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(employeeCollection, employee);
      expect(comp.employeesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const planificateur: IPlanificateur = { id: 1147 };
      const employee: IEmployee = { id: 1749 };
      planificateur.employee = employee;

      activatedRoute.data = of({ planificateur });
      comp.ngOnInit();

      expect(comp.employeesCollection).toContainEqual(employee);
      expect(comp.planificateur).toEqual(planificateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanificateur>>();
      const planificateur = { id: 18052 };
      jest.spyOn(planificateurFormService, 'getPlanificateur').mockReturnValue(planificateur);
      jest.spyOn(planificateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planificateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planificateur }));
      saveSubject.complete();

      // THEN
      expect(planificateurFormService.getPlanificateur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(planificateurService.update).toHaveBeenCalledWith(expect.objectContaining(planificateur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanificateur>>();
      const planificateur = { id: 18052 };
      jest.spyOn(planificateurFormService, 'getPlanificateur').mockReturnValue({ id: null });
      jest.spyOn(planificateurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planificateur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planificateur }));
      saveSubject.complete();

      // THEN
      expect(planificateurFormService.getPlanificateur).toHaveBeenCalled();
      expect(planificateurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanificateur>>();
      const planificateur = { id: 18052 };
      jest.spyOn(planificateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planificateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(planificateurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 1749 };
        const entity2 = { id: 1545 };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
