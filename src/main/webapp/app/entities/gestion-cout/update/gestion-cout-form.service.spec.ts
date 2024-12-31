import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../gestion-cout.test-samples';

import { GestionCoutFormService } from './gestion-cout-form.service';

describe('GestionCout Form Service', () => {
  let service: GestionCoutFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GestionCoutFormService);
  });

  describe('Service methods', () => {
    describe('createGestionCoutFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGestionCoutFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            time: expect.any(Object),
            cout: expect.any(Object),
            planificateur: expect.any(Object),
          }),
        );
      });

      it('passing IGestionCout should create a new form with FormGroup', () => {
        const formGroup = service.createGestionCoutFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            time: expect.any(Object),
            cout: expect.any(Object),
            planificateur: expect.any(Object),
          }),
        );
      });
    });

    describe('getGestionCout', () => {
      it('should return NewGestionCout for default GestionCout initial value', () => {
        const formGroup = service.createGestionCoutFormGroup(sampleWithNewData);

        const gestionCout = service.getGestionCout(formGroup) as any;

        expect(gestionCout).toMatchObject(sampleWithNewData);
      });

      it('should return NewGestionCout for empty GestionCout initial value', () => {
        const formGroup = service.createGestionCoutFormGroup();

        const gestionCout = service.getGestionCout(formGroup) as any;

        expect(gestionCout).toMatchObject({});
      });

      it('should return IGestionCout', () => {
        const formGroup = service.createGestionCoutFormGroup(sampleWithRequiredData);

        const gestionCout = service.getGestionCout(formGroup) as any;

        expect(gestionCout).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGestionCout should not enable id FormControl', () => {
        const formGroup = service.createGestionCoutFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGestionCout should disable id FormControl', () => {
        const formGroup = service.createGestionCoutFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
