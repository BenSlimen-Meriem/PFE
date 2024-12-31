import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IMotif, NewMotif } from '../motif.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMotif for edit and NewMotifFormGroupInput for create.
 */
type MotifFormGroupInput = IMotif | PartialWithRequiredKeyOf<NewMotif>;

type MotifFormDefaults = Pick<NewMotif, 'id'>;

type MotifFormGroupContent = {
  id: FormControl<IMotif['id'] | NewMotif['id']>;
  code: FormControl<IMotif['code']>;
  libelle: FormControl<IMotif['libelle']>;
  description: FormControl<IMotif['description']>;
  priorite: FormControl<IMotif['priorite']>;
};

export type MotifFormGroup = FormGroup<MotifFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MotifFormService {
  createMotifFormGroup(motif: MotifFormGroupInput = { id: null }): MotifFormGroup {
    const motifRawValue = {
      ...this.getFormDefaults(),
      ...motif,
    };
    return new FormGroup<MotifFormGroupContent>({
      id: new FormControl(
        { value: motifRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(motifRawValue.code, {
        validators: [Validators.required],
      }),
      libelle: new FormControl(motifRawValue.libelle, {
        validators: [Validators.required],
      }),
      description: new FormControl(motifRawValue.description),
      priorite: new FormControl(motifRawValue.priorite),
    });
  }

  getMotif(form: MotifFormGroup): IMotif | NewMotif {
    return form.getRawValue() as IMotif | NewMotif;
  }

  resetForm(form: MotifFormGroup, motif: MotifFormGroupInput): void {
    const motifRawValue = { ...this.getFormDefaults(), ...motif };
    form.reset(
      {
        ...motifRawValue,
        id: { value: motifRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MotifFormDefaults {
    return {
      id: null,
    };
  }
}
