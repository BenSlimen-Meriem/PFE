import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../planificateur.test-samples';

import { PlanificateurFormService } from './planificateur-form.service';

describe('Planificateur Form Service', () => {
  let service: PlanificateurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanificateurFormService);
  });

  describe('Service methods', () => {
    describe('createPlanificateurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlanificateurFormGroup();

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

      it('passing IPlanificateur should create a new form with FormGroup', () => {
        const formGroup = service.createPlanificateurFormGroup(sampleWithRequiredData);

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

    describe('getPlanificateur', () => {
      it('should return NewPlanificateur for default Planificateur initial value', () => {
        const formGroup = service.createPlanificateurFormGroup(sampleWithNewData);

        const planificateur = service.getPlanificateur(formGroup) as any;

        expect(planificateur).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlanificateur for empty Planificateur initial value', () => {
        const formGroup = service.createPlanificateurFormGroup();

        const planificateur = service.getPlanificateur(formGroup) as any;

        expect(planificateur).toMatchObject({});
      });

      it('should return IPlanificateur', () => {
        const formGroup = service.createPlanificateurFormGroup(sampleWithRequiredData);

        const planificateur = service.getPlanificateur(formGroup) as any;

        expect(planificateur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlanificateur should not enable id FormControl', () => {
        const formGroup = service.createPlanificateurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlanificateur should disable id FormControl', () => {
        const formGroup = service.createPlanificateurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
