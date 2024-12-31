import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../motif.test-samples';

import { MotifFormService } from './motif-form.service';

describe('Motif Form Service', () => {
  let service: MotifFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MotifFormService);
  });

  describe('Service methods', () => {
    describe('createMotifFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMotifFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
            priorite: expect.any(Object),
          }),
        );
      });

      it('passing IMotif should create a new form with FormGroup', () => {
        const formGroup = service.createMotifFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            description: expect.any(Object),
            priorite: expect.any(Object),
          }),
        );
      });
    });

    describe('getMotif', () => {
      it('should return NewMotif for default Motif initial value', () => {
        const formGroup = service.createMotifFormGroup(sampleWithNewData);

        const motif = service.getMotif(formGroup) as any;

        expect(motif).toMatchObject(sampleWithNewData);
      });

      it('should return NewMotif for empty Motif initial value', () => {
        const formGroup = service.createMotifFormGroup();

        const motif = service.getMotif(formGroup) as any;

        expect(motif).toMatchObject({});
      });

      it('should return IMotif', () => {
        const formGroup = service.createMotifFormGroup(sampleWithRequiredData);

        const motif = service.getMotif(formGroup) as any;

        expect(motif).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMotif should not enable id FormControl', () => {
        const formGroup = service.createMotifFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMotif should disable id FormControl', () => {
        const formGroup = service.createMotifFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
