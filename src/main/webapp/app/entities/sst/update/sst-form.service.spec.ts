import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sst.test-samples';

import { SSTFormService } from './sst-form.service';

describe('SST Form Service', () => {
  let service: SSTFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SSTFormService);
  });

  describe('Service methods', () => {
    describe('createSSTFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSSTFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });

      it('passing ISST should create a new form with FormGroup', () => {
        const formGroup = service.createSSTFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });
    });

    describe('getSST', () => {
      it('should return NewSST for default SST initial value', () => {
        const formGroup = service.createSSTFormGroup(sampleWithNewData);

        const sST = service.getSST(formGroup) as any;

        expect(sST).toMatchObject(sampleWithNewData);
      });

      it('should return NewSST for empty SST initial value', () => {
        const formGroup = service.createSSTFormGroup();

        const sST = service.getSST(formGroup) as any;

        expect(sST).toMatchObject({});
      });

      it('should return ISST', () => {
        const formGroup = service.createSSTFormGroup(sampleWithRequiredData);

        const sST = service.getSST(formGroup) as any;

        expect(sST).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISST should not enable id FormControl', () => {
        const formGroup = service.createSSTFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSST should disable id FormControl', () => {
        const formGroup = service.createSSTFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
