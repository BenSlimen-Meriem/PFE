import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../work-order.test-samples';

import { WorkOrderFormService } from './work-order-form.service';

describe('WorkOrder Form Service', () => {
  let service: WorkOrderFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkOrderFormService);
  });

  describe('Service methods', () => {
    describe('createWorkOrderFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createWorkOrderFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            demandeur: expect.any(Object),
            nature: expect.any(Object),
            motifDescription: expect.any(Object),
            dateHeureDebutPrevisionnelle: expect.any(Object),
            dateHeureFinPrevisionnelle: expect.any(Object),
            vehicule: expect.any(Object),
            materielUtilise: expect.any(Object),
            article: expect.any(Object),
            membreMission: expect.any(Object),
            responsableMission: expect.any(Object),
            statut: expect.any(Object),
            affaire: expect.any(Object),
            motif: expect.any(Object),
            employees: expect.any(Object),
            articles: expect.any(Object),
            vehicules: expect.any(Object),
            site: expect.any(Object),
          }),
        );
      });

      it('passing IWorkOrder should create a new form with FormGroup', () => {
        const formGroup = service.createWorkOrderFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            demandeur: expect.any(Object),
            nature: expect.any(Object),
            motifDescription: expect.any(Object),
            dateHeureDebutPrevisionnelle: expect.any(Object),
            dateHeureFinPrevisionnelle: expect.any(Object),
            vehicule: expect.any(Object),
            materielUtilise: expect.any(Object),
            article: expect.any(Object),
            membreMission: expect.any(Object),
            responsableMission: expect.any(Object),
            statut: expect.any(Object),
            affaire: expect.any(Object),
            motif: expect.any(Object),
            employees: expect.any(Object),
            articles: expect.any(Object),
            vehicules: expect.any(Object),
            site: expect.any(Object),
          }),
        );
      });
    });

    describe('getWorkOrder', () => {
      it('should return NewWorkOrder for default WorkOrder initial value', () => {
        const formGroup = service.createWorkOrderFormGroup(sampleWithNewData);

        const workOrder = service.getWorkOrder(formGroup) as any;

        expect(workOrder).toMatchObject(sampleWithNewData);
      });

      it('should return NewWorkOrder for empty WorkOrder initial value', () => {
        const formGroup = service.createWorkOrderFormGroup();

        const workOrder = service.getWorkOrder(formGroup) as any;

        expect(workOrder).toMatchObject({});
      });

      it('should return IWorkOrder', () => {
        const formGroup = service.createWorkOrderFormGroup(sampleWithRequiredData);

        const workOrder = service.getWorkOrder(formGroup) as any;

        expect(workOrder).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IWorkOrder should not enable id FormControl', () => {
        const formGroup = service.createWorkOrderFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewWorkOrder should disable id FormControl', () => {
        const formGroup = service.createWorkOrderFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
