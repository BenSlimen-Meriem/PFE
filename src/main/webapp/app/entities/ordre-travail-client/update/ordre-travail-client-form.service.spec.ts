import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../ordre-travail-client.test-samples';

import { OrdreTravailClientFormService } from './ordre-travail-client-form.service';

describe('OrdreTravailClient Form Service', () => {
  let service: OrdreTravailClientFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrdreTravailClientFormService);
  });

  describe('Service methods', () => {
    describe('createOrdreTravailClientFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrdreTravailClientFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fraisSession: expect.any(Object),
            article: expect.any(Object),
            workOrder: expect.any(Object),
          }),
        );
      });

      it('passing IOrdreTravailClient should create a new form with FormGroup', () => {
        const formGroup = service.createOrdreTravailClientFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fraisSession: expect.any(Object),
            article: expect.any(Object),
            workOrder: expect.any(Object),
          }),
        );
      });
    });

    describe('getOrdreTravailClient', () => {
      it('should return NewOrdreTravailClient for default OrdreTravailClient initial value', () => {
        const formGroup = service.createOrdreTravailClientFormGroup(sampleWithNewData);

        const ordreTravailClient = service.getOrdreTravailClient(formGroup) as any;

        expect(ordreTravailClient).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrdreTravailClient for empty OrdreTravailClient initial value', () => {
        const formGroup = service.createOrdreTravailClientFormGroup();

        const ordreTravailClient = service.getOrdreTravailClient(formGroup) as any;

        expect(ordreTravailClient).toMatchObject({});
      });

      it('should return IOrdreTravailClient', () => {
        const formGroup = service.createOrdreTravailClientFormGroup(sampleWithRequiredData);

        const ordreTravailClient = service.getOrdreTravailClient(formGroup) as any;

        expect(ordreTravailClient).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrdreTravailClient should not enable id FormControl', () => {
        const formGroup = service.createOrdreTravailClientFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrdreTravailClient should disable id FormControl', () => {
        const formGroup = service.createOrdreTravailClientFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
