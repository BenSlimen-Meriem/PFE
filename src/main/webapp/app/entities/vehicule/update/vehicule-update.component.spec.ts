import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IWorkOrder } from 'app/entities/work-order/work-order.model';
import { WorkOrderService } from 'app/entities/work-order/service/work-order.service';
import { VehiculeService } from '../service/vehicule.service';
import { IVehicule } from '../vehicule.model';
import { VehiculeFormService } from './vehicule-form.service';

import { VehiculeUpdateComponent } from './vehicule-update.component';

describe('Vehicule Management Update Component', () => {
  let comp: VehiculeUpdateComponent;
  let fixture: ComponentFixture<VehiculeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let vehiculeFormService: VehiculeFormService;
  let vehiculeService: VehiculeService;
  let workOrderService: WorkOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [VehiculeUpdateComponent],
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
      .overrideTemplate(VehiculeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VehiculeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    vehiculeFormService = TestBed.inject(VehiculeFormService);
    vehiculeService = TestBed.inject(VehiculeService);
    workOrderService = TestBed.inject(WorkOrderService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call WorkOrder query and add missing value', () => {
      const vehicule: IVehicule = { id: 5417 };
      const workOrders: IWorkOrder[] = [{ id: 28118 }];
      vehicule.workOrders = workOrders;

      const workOrderCollection: IWorkOrder[] = [{ id: 28118 }];
      jest.spyOn(workOrderService, 'query').mockReturnValue(of(new HttpResponse({ body: workOrderCollection })));
      const additionalWorkOrders = [...workOrders];
      const expectedCollection: IWorkOrder[] = [...additionalWorkOrders, ...workOrderCollection];
      jest.spyOn(workOrderService, 'addWorkOrderToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ vehicule });
      comp.ngOnInit();

      expect(workOrderService.query).toHaveBeenCalled();
      expect(workOrderService.addWorkOrderToCollectionIfMissing).toHaveBeenCalledWith(
        workOrderCollection,
        ...additionalWorkOrders.map(expect.objectContaining),
      );
      expect(comp.workOrdersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const vehicule: IVehicule = { id: 5417 };
      const workOrder: IWorkOrder = { id: 28118 };
      vehicule.workOrders = [workOrder];

      activatedRoute.data = of({ vehicule });
      comp.ngOnInit();

      expect(comp.workOrdersSharedCollection).toContainEqual(workOrder);
      expect(comp.vehicule).toEqual(vehicule);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVehicule>>();
      const vehicule = { id: 32197 };
      jest.spyOn(vehiculeFormService, 'getVehicule').mockReturnValue(vehicule);
      jest.spyOn(vehiculeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vehicule }));
      saveSubject.complete();

      // THEN
      expect(vehiculeFormService.getVehicule).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(vehiculeService.update).toHaveBeenCalledWith(expect.objectContaining(vehicule));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVehicule>>();
      const vehicule = { id: 32197 };
      jest.spyOn(vehiculeFormService, 'getVehicule').mockReturnValue({ id: null });
      jest.spyOn(vehiculeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicule: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vehicule }));
      saveSubject.complete();

      // THEN
      expect(vehiculeFormService.getVehicule).toHaveBeenCalled();
      expect(vehiculeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVehicule>>();
      const vehicule = { id: 32197 };
      jest.spyOn(vehiculeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(vehiculeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareWorkOrder', () => {
      it('Should forward to workOrderService', () => {
        const entity = { id: 28118 };
        const entity2 = { id: 6339 };
        jest.spyOn(workOrderService, 'compareWorkOrder');
        comp.compareWorkOrder(entity, entity2);
        expect(workOrderService.compareWorkOrder).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
