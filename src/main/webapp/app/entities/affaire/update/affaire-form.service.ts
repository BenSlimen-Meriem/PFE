import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAffaire, NewAffaire } from '../affaire.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAffaire for edit and NewAffaireFormGroupInput for create.
 */
type AffaireFormGroupInput = IAffaire | PartialWithRequiredKeyOf<NewAffaire>;

type AffaireFormDefaults = Pick<NewAffaire, 'id'>;

type AffaireFormGroupContent = {
  id: FormControl<IAffaire['id'] | NewAffaire['id']>;
  reference: FormControl<IAffaire['reference']>;
  description: FormControl<IAffaire['description']>;
  client: FormControl<IAffaire['client']>;
};

export type AffaireFormGroup = FormGroup<AffaireFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AffaireFormService {
  createAffaireFormGroup(affaire: AffaireFormGroupInput = { id: null }): AffaireFormGroup {
    const affaireRawValue = {
      ...this.getFormDefaults(),
      ...affaire,
    };
    return new FormGroup<AffaireFormGroupContent>({
      id: new FormControl(
        { value: affaireRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      reference: new FormControl(affaireRawValue.reference, {
        validators: [Validators.required],
      }),
      description: new FormControl(affaireRawValue.description),
      client: new FormControl(affaireRawValue.client),
    });
  }

  getAffaire(form: AffaireFormGroup): IAffaire | NewAffaire {
    return form.getRawValue() as IAffaire | NewAffaire;
  }

  resetForm(form: AffaireFormGroup, affaire: AffaireFormGroupInput): void {
    const affaireRawValue = { ...this.getFormDefaults(), ...affaire };
    form.reset(
      {
        ...affaireRawValue,
        id: { value: affaireRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AffaireFormDefaults {
    return {
      id: null,
    };
  }
}
