import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IWorkOrder } from 'app/entities/work-order/work-order.model';
import { WorkOrderService } from 'app/entities/work-order/service/work-order.service';
import { OrdreFacturationService } from '../service/ordre-facturation.service';
import { IOrdreFacturation } from '../ordre-facturation.model';
import { OrdreFacturationFormService } from './ordre-facturation-form.service';

import { OrdreFacturationUpdateComponent } from './ordre-facturation-update.component';

describe('OrdreFacturation Management Update Component', () => {
  let comp: OrdreFacturationUpdateComponent;
  let fixture: ComponentFixture<OrdreFacturationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ordreFacturationFormService: OrdreFacturationFormService;
  let ordreFacturationService: OrdreFacturationService;
  let workOrderService: WorkOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrdreFacturationUpdateComponent],
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
      .overrideTemplate(OrdreFacturationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrdreFacturationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ordreFacturationFormService = TestBed.inject(OrdreFacturationFormService);
    ordreFacturationService = TestBed.inject(OrdreFacturationService);
    workOrderService = TestBed.inject(WorkOrderService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call WorkOrder query and add missing value', () => {
      const ordreFacturation: IOrdreFacturation = { id: 11283 };
      const workOrder: IWorkOrder = { id: 28118 };
      ordreFacturation.workOrder = workOrder;

      const workOrderCollection: IWorkOrder[] = [{ id: 28118 }];
      jest.spyOn(workOrderService, 'query').mockReturnValue(of(new HttpResponse({ body: workOrderCollection })));
      const additionalWorkOrders = [workOrder];
      const expectedCollection: IWorkOrder[] = [...additionalWorkOrders, ...workOrderCollection];
      jest.spyOn(workOrderService, 'addWorkOrderToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ordreFacturation });
      comp.ngOnInit();

      expect(workOrderService.query).toHaveBeenCalled();
      expect(workOrderService.addWorkOrderToCollectionIfMissing).toHaveBeenCalledWith(
        workOrderCollection,
        ...additionalWorkOrders.map(expect.objectContaining),
      );
      expect(comp.workOrdersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ordreFacturation: IOrdreFacturation = { id: 11283 };
      const workOrder: IWorkOrder = { id: 28118 };
      ordreFacturation.workOrder = workOrder;

      activatedRoute.data = of({ ordreFacturation });
      comp.ngOnInit();

      expect(comp.workOrdersSharedCollection).toContainEqual(workOrder);
      expect(comp.ordreFacturation).toEqual(ordreFacturation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdreFacturation>>();
      const ordreFacturation = { id: 31844 };
      jest.spyOn(ordreFacturationFormService, 'getOrdreFacturation').mockReturnValue(ordreFacturation);
      jest.spyOn(ordreFacturationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordreFacturation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ordreFacturation }));
      saveSubject.complete();

      // THEN
      expect(ordreFacturationFormService.getOrdreFacturation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ordreFacturationService.update).toHaveBeenCalledWith(expect.objectContaining(ordreFacturation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdreFacturation>>();
      const ordreFacturation = { id: 31844 };
      jest.spyOn(ordreFacturationFormService, 'getOrdreFacturation').mockReturnValue({ id: null });
      jest.spyOn(ordreFacturationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordreFacturation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ordreFacturation }));
      saveSubject.complete();

      // THEN
      expect(ordreFacturationFormService.getOrdreFacturation).toHaveBeenCalled();
      expect(ordreFacturationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdreFacturation>>();
      const ordreFacturation = { id: 31844 };
      jest.spyOn(ordreFacturationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordreFacturation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ordreFacturationService.update).toHaveBeenCalled();
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
