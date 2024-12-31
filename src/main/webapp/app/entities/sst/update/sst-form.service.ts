import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISST, NewSST } from '../sst.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISST for edit and NewSSTFormGroupInput for create.
 */
type SSTFormGroupInput = ISST | PartialWithRequiredKeyOf<NewSST>;

type SSTFormDefaults = Pick<NewSST, 'id'>;

type SSTFormGroupContent = {
  id: FormControl<ISST['id'] | NewSST['id']>;
  description: FormControl<ISST['description']>;
};

export type SSTFormGroup = FormGroup<SSTFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SSTFormService {
  createSSTFormGroup(sST: SSTFormGroupInput = { id: null }): SSTFormGroup {
    const sSTRawValue = {
      ...this.getFormDefaults(),
      ...sST,
    };
    return new FormGroup<SSTFormGroupContent>({
      id: new FormControl(
        { value: sSTRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      description: new FormControl(sSTRawValue.description),
    });
  }

  getSST(form: SSTFormGroup): ISST | NewSST {
    return form.getRawValue() as ISST | NewSST;
  }

  resetForm(form: SSTFormGroup, sST: SSTFormGroupInput): void {
    const sSTRawValue = { ...this.getFormDefaults(), ...sST };
    form.reset(
      {
        ...sSTRawValue,
        id: { value: sSTRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SSTFormDefaults {
    return {
      id: null,
    };
  }
}
