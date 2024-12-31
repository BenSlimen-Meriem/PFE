import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IOrdreTravailClient, NewOrdreTravailClient } from '../ordre-travail-client.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrdreTravailClient for edit and NewOrdreTravailClientFormGroupInput for create.
 */
type OrdreTravailClientFormGroupInput = IOrdreTravailClient | PartialWithRequiredKeyOf<NewOrdreTravailClient>;

type OrdreTravailClientFormDefaults = Pick<NewOrdreTravailClient, 'id'>;

type OrdreTravailClientFormGroupContent = {
  id: FormControl<IOrdreTravailClient['id'] | NewOrdreTravailClient['id']>;
  fraisSession: FormControl<IOrdreTravailClient['fraisSession']>;
  article: FormControl<IOrdreTravailClient['article']>;
  workOrder: FormControl<IOrdreTravailClient['workOrder']>;
};

export type OrdreTravailClientFormGroup = FormGroup<OrdreTravailClientFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrdreTravailClientFormService {
  createOrdreTravailClientFormGroup(ordreTravailClient: OrdreTravailClientFormGroupInput = { id: null }): OrdreTravailClientFormGroup {
    const ordreTravailClientRawValue = {
      ...this.getFormDefaults(),
      ...ordreTravailClient,
    };
    return new FormGroup<OrdreTravailClientFormGroupContent>({
      id: new FormControl(
        { value: ordreTravailClientRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fraisSession: new FormControl(ordreTravailClientRawValue.fraisSession, {
        validators: [Validators.required],
      }),
      article: new FormControl(ordreTravailClientRawValue.article),
      workOrder: new FormControl(ordreTravailClientRawValue.workOrder),
    });
  }

  getOrdreTravailClient(form: OrdreTravailClientFormGroup): IOrdreTravailClient | NewOrdreTravailClient {
    return form.getRawValue() as IOrdreTravailClient | NewOrdreTravailClient;
  }

  resetForm(form: OrdreTravailClientFormGroup, ordreTravailClient: OrdreTravailClientFormGroupInput): void {
    const ordreTravailClientRawValue = { ...this.getFormDefaults(), ...ordreTravailClient };
    form.reset(
      {
        ...ordreTravailClientRawValue,
        id: { value: ordreTravailClientRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OrdreTravailClientFormDefaults {
    return {
      id: null,
    };
  }
}
