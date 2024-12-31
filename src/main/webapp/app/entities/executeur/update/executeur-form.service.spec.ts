import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../executeur.test-samples';

import { ExecuteurFormService } from './executeur-form.service';

describe('Executeur Form Service', () => {
  let service: ExecuteurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExecuteurFormService);
  });

  describe('Service methods', () => {
    describe('createExecuteurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createExecuteurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            niveauExpertise: expect.any(Object),
            disponibilite: expect.any(Object),
            employee: expect.any(Object),
          }),
        );
      });

      it('passing IExecuteur should create a new form with FormGroup', () => {
        const formGroup = service.createExecuteurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            niveauExpertise: expect.any(Object),
            disponibilite: expect.any(Object),
            employee: expect.any(Object),
          }),
        );
      });
    });

    describe('getExecuteur', () => {
      it('should return NewExecuteur for default Executeur initial value', () => {
        const formGroup = service.createExecuteurFormGroup(sampleWithNewData);

        const executeur = service.getExecuteur(formGroup) as any;

        expect(executeur).toMatchObject(sampleWithNewData);
      });

      it('should return NewExecuteur for empty Executeur initial value', () => {
        const formGroup = service.createExecuteurFormGroup();

        const executeur = service.getExecuteur(formGroup) as any;

        expect(executeur).toMatchObject({});
      });

      it('should return IExecuteur', () => {
        const formGroup = service.createExecuteurFormGroup(sampleWithRequiredData);

        const executeur = service.getExecuteur(formGroup) as any;

        expect(executeur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IExecuteur should not enable id FormControl', () => {
        const formGroup = service.createExecuteurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewExecuteur should disable id FormControl', () => {
        const formGroup = service.createExecuteurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
