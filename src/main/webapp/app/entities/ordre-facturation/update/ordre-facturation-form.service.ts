import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IOrdreFacturation, NewOrdreFacturation } from '../ordre-facturation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrdreFacturation for edit and NewOrdreFacturationFormGroupInput for create.
 */
type OrdreFacturationFormGroupInput = IOrdreFacturation | PartialWithRequiredKeyOf<NewOrdreFacturation>;

type OrdreFacturationFormDefaults = Pick<NewOrdreFacturation, 'id'>;

type OrdreFacturationFormGroupContent = {
  id: FormControl<IOrdreFacturation['id'] | NewOrdreFacturation['id']>;
  date: FormControl<IOrdreFacturation['date']>;
  bonDeCommande: FormControl<IOrdreFacturation['bonDeCommande']>;
  facture: FormControl<IOrdreFacturation['facture']>;
  workOrder: FormControl<IOrdreFacturation['workOrder']>;
};

export type OrdreFacturationFormGroup = FormGroup<OrdreFacturationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrdreFacturationFormService {
  createOrdreFacturationFormGroup(ordreFacturation: OrdreFacturationFormGroupInput = { id: null }): OrdreFacturationFormGroup {
    const ordreFacturationRawValue = {
      ...this.getFormDefaults(),
      ...ordreFacturation,
    };
    return new FormGroup<OrdreFacturationFormGroupContent>({
      id: new FormControl(
        { value: ordreFacturationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      date: new FormControl(ordreFacturationRawValue.date, {
        validators: [Validators.required],
      }),
      bonDeCommande: new FormControl(ordreFacturationRawValue.bonDeCommande),
      facture: new FormControl(ordreFacturationRawValue.facture),
      workOrder: new FormControl(ordreFacturationRawValue.workOrder),
    });
  }

  getOrdreFacturation(form: OrdreFacturationFormGroup): IOrdreFacturation | NewOrdreFacturation {
    return form.getRawValue() as IOrdreFacturation | NewOrdreFacturation;
  }

  resetForm(form: OrdreFacturationFormGroup, ordreFacturation: OrdreFacturationFormGroupInput): void {
    const ordreFacturationRawValue = { ...this.getFormDefaults(), ...ordreFacturation };
    form.reset(
      {
        ...ordreFacturationRawValue,
        id: { value: ordreFacturationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OrdreFacturationFormDefaults {
    return {
      id: null,
    };
  }
}
