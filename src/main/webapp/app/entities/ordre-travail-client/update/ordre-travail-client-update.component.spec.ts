import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IWorkOrder } from 'app/entities/work-order/work-order.model';
import { WorkOrderService } from 'app/entities/work-order/service/work-order.service';
import { OrdreTravailClientService } from '../service/ordre-travail-client.service';
import { IOrdreTravailClient } from '../ordre-travail-client.model';
import { OrdreTravailClientFormService } from './ordre-travail-client-form.service';

import { OrdreTravailClientUpdateComponent } from './ordre-travail-client-update.component';

describe('OrdreTravailClient Management Update Component', () => {
  let comp: OrdreTravailClientUpdateComponent;
  let fixture: ComponentFixture<OrdreTravailClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ordreTravailClientFormService: OrdreTravailClientFormService;
  let ordreTravailClientService: OrdreTravailClientService;
  let workOrderService: WorkOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrdreTravailClientUpdateComponent],
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
      .overrideTemplate(OrdreTravailClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrdreTravailClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ordreTravailClientFormService = TestBed.inject(OrdreTravailClientFormService);
    ordreTravailClientService = TestBed.inject(OrdreTravailClientService);
    workOrderService = TestBed.inject(WorkOrderService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call WorkOrder query and add missing value', () => {
      const ordreTravailClient: IOrdreTravailClient = { id: 1464 };
      const workOrder: IWorkOrder = { id: 28118 };
      ordreTravailClient.workOrder = workOrder;

      const workOrderCollection: IWorkOrder[] = [{ id: 28118 }];
      jest.spyOn(workOrderService, 'query').mockReturnValue(of(new HttpResponse({ body: workOrderCollection })));
      const additionalWorkOrders = [workOrder];
      const expectedCollection: IWorkOrder[] = [...additionalWorkOrders, ...workOrderCollection];
      jest.spyOn(workOrderService, 'addWorkOrderToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ordreTravailClient });
      comp.ngOnInit();

      expect(workOrderService.query).toHaveBeenCalled();
      expect(workOrderService.addWorkOrderToCollectionIfMissing).toHaveBeenCalledWith(
        workOrderCollection,
        ...additionalWorkOrders.map(expect.objectContaining),
      );
      expect(comp.workOrdersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ordreTravailClient: IOrdreTravailClient = { id: 1464 };
      const workOrder: IWorkOrder = { id: 28118 };
      ordreTravailClient.workOrder = workOrder;

      activatedRoute.data = of({ ordreTravailClient });
      comp.ngOnInit();

      expect(comp.workOrdersSharedCollection).toContainEqual(workOrder);
      expect(comp.ordreTravailClient).toEqual(ordreTravailClient);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdreTravailClient>>();
      const ordreTravailClient = { id: 24849 };
      jest.spyOn(ordreTravailClientFormService, 'getOrdreTravailClient').mockReturnValue(ordreTravailClient);
      jest.spyOn(ordreTravailClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordreTravailClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ordreTravailClient }));
      saveSubject.complete();

      // THEN
      expect(ordreTravailClientFormService.getOrdreTravailClient).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ordreTravailClientService.update).toHaveBeenCalledWith(expect.objectContaining(ordreTravailClient));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdreTravailClient>>();
      const ordreTravailClient = { id: 24849 };
      jest.spyOn(ordreTravailClientFormService, 'getOrdreTravailClient').mockReturnValue({ id: null });
      jest.spyOn(ordreTravailClientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordreTravailClient: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ordreTravailClient }));
      saveSubject.complete();

      // THEN
      expect(ordreTravailClientFormService.getOrdreTravailClient).toHaveBeenCalled();
      expect(ordreTravailClientService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdreTravailClient>>();
      const ordreTravailClient = { id: 24849 };
      jest.spyOn(ordreTravailClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordreTravailClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ordreTravailClientService.update).toHaveBeenCalled();
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
