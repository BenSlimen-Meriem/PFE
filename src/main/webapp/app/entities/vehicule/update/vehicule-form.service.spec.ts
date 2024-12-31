import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../vehicule.test-samples';

import { VehiculeFormService } from './vehicule-form.service';

describe('Vehicule Form Service', () => {
  let service: VehiculeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VehiculeFormService);
  });

  describe('Service methods', () => {
    describe('createVehiculeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVehiculeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            model: expect.any(Object),
            numeroCarteGrise: expect.any(Object),
            numSerie: expect.any(Object),
            statut: expect.any(Object),
            type: expect.any(Object),
            workOrders: expect.any(Object),
          }),
        );
      });

      it('passing IVehicule should create a new form with FormGroup', () => {
        const formGroup = service.createVehiculeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            model: expect.any(Object),
            numeroCarteGrise: expect.any(Object),
            numSerie: expect.any(Object),
            statut: expect.any(Object),
            type: expect.any(Object),
            workOrders: expect.any(Object),
          }),
        );
      });
    });

    describe('getVehicule', () => {
      it('should return NewVehicule for default Vehicule initial value', () => {
        const formGroup = service.createVehiculeFormGroup(sampleWithNewData);

        const vehicule = service.getVehicule(formGroup) as any;

        expect(vehicule).toMatchObject(sampleWithNewData);
      });

      it('should return NewVehicule for empty Vehicule initial value', () => {
        const formGroup = service.createVehiculeFormGroup();

        const vehicule = service.getVehicule(formGroup) as any;

        expect(vehicule).toMatchObject({});
      });

      it('should return IVehicule', () => {
        const formGroup = service.createVehiculeFormGroup(sampleWithRequiredData);

        const vehicule = service.getVehicule(formGroup) as any;

        expect(vehicule).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVehicule should not enable id FormControl', () => {
        const formGroup = service.createVehiculeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVehicule should disable id FormControl', () => {
        const formGroup = service.createVehiculeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
