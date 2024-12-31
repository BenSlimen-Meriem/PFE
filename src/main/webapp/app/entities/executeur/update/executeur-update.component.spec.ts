import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ExecuteurService } from '../service/executeur.service';
import { IExecuteur } from '../executeur.model';
import { ExecuteurFormService } from './executeur-form.service';

import { ExecuteurUpdateComponent } from './executeur-update.component';

describe('Executeur Management Update Component', () => {
  let comp: ExecuteurUpdateComponent;
  let fixture: ComponentFixture<ExecuteurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let executeurFormService: ExecuteurFormService;
  let executeurService: ExecuteurService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ExecuteurUpdateComponent],
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
      .overrideTemplate(ExecuteurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExecuteurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    executeurFormService = TestBed.inject(ExecuteurFormService);
    executeurService = TestBed.inject(ExecuteurService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call employee query and add missing value', () => {
      const executeur: IExecuteur = { id: 20209 };
      const employee: IEmployee = { id: 1749 };
      executeur.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 1749 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const expectedCollection: IEmployee[] = [employee, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ executeur });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(employeeCollection, employee);
      expect(comp.employeesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const executeur: IExecuteur = { id: 20209 };
      const employee: IEmployee = { id: 1749 };
      executeur.employee = employee;

      activatedRoute.data = of({ executeur });
      comp.ngOnInit();

      expect(comp.employeesCollection).toContainEqual(employee);
      expect(comp.executeur).toEqual(executeur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExecuteur>>();
      const executeur = { id: 25266 };
      jest.spyOn(executeurFormService, 'getExecuteur').mockReturnValue(executeur);
      jest.spyOn(executeurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ executeur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: executeur }));
      saveSubject.complete();

      // THEN
      expect(executeurFormService.getExecuteur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(executeurService.update).toHaveBeenCalledWith(expect.objectContaining(executeur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExecuteur>>();
      const executeur = { id: 25266 };
      jest.spyOn(executeurFormService, 'getExecuteur').mockReturnValue({ id: null });
      jest.spyOn(executeurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ executeur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: executeur }));
      saveSubject.complete();

      // THEN
      expect(executeurFormService.getExecuteur).toHaveBeenCalled();
      expect(executeurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExecuteur>>();
      const executeur = { id: 25266 };
      jest.spyOn(executeurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ executeur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(executeurService.update).toHaveBeenCalled();
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
