import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../ordre-facturation.test-samples';

import { OrdreFacturationFormService } from './ordre-facturation-form.service';

describe('OrdreFacturation Form Service', () => {
  let service: OrdreFacturationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrdreFacturationFormService);
  });

  describe('Service methods', () => {
    describe('createOrdreFacturationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrdreFacturationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            bonDeCommande: expect.any(Object),
            facture: expect.any(Object),
            workOrder: expect.any(Object),
          }),
        );
      });

      it('passing IOrdreFacturation should create a new form with FormGroup', () => {
        const formGroup = service.createOrdreFacturationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            bonDeCommande: expect.any(Object),
            facture: expect.any(Object),
            workOrder: expect.any(Object),
          }),
        );
      });
    });

    describe('getOrdreFacturation', () => {
      it('should return NewOrdreFacturation for default OrdreFacturation initial value', () => {
        const formGroup = service.createOrdreFacturationFormGroup(sampleWithNewData);

        const ordreFacturation = service.getOrdreFacturation(formGroup) as any;

        expect(ordreFacturation).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrdreFacturation for empty OrdreFacturation initial value', () => {
        const formGroup = service.createOrdreFacturationFormGroup();

        const ordreFacturation = service.getOrdreFacturation(formGroup) as any;

        expect(ordreFacturation).toMatchObject({});
      });

      it('should return IOrdreFacturation', () => {
        const formGroup = service.createOrdreFacturationFormGroup(sampleWithRequiredData);

        const ordreFacturation = service.getOrdreFacturation(formGroup) as any;

        expect(ordreFacturation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrdreFacturation should not enable id FormControl', () => {
        const formGroup = service.createOrdreFacturationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrdreFacturation should disable id FormControl', () => {
        const formGroup = service.createOrdreFacturationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
