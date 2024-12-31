import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IExecuteur, NewExecuteur } from '../executeur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IExecuteur for edit and NewExecuteurFormGroupInput for create.
 */
type ExecuteurFormGroupInput = IExecuteur | PartialWithRequiredKeyOf<NewExecuteur>;

type ExecuteurFormDefaults = Pick<NewExecuteur, 'id' | 'disponibilite'>;

type ExecuteurFormGroupContent = {
  id: FormControl<IExecuteur['id'] | NewExecuteur['id']>;
  nom: FormControl<IExecuteur['nom']>;
  niveauExpertise: FormControl<IExecuteur['niveauExpertise']>;
  disponibilite: FormControl<IExecuteur['disponibilite']>;
  employee: FormControl<IExecuteur['employee']>;
};

export type ExecuteurFormGroup = FormGroup<ExecuteurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ExecuteurFormService {
  createExecuteurFormGroup(executeur: ExecuteurFormGroupInput = { id: null }): ExecuteurFormGroup {
    const executeurRawValue = {
      ...this.getFormDefaults(),
      ...executeur,
    };
    return new FormGroup<ExecuteurFormGroupContent>({
      id: new FormControl(
        { value: executeurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(executeurRawValue.nom, {
        validators: [Validators.required],
      }),
      niveauExpertise: new FormControl(executeurRawValue.niveauExpertise, {
        validators: [Validators.required],
      }),
      disponibilite: new FormControl(executeurRawValue.disponibilite, {
        validators: [Validators.required],
      }),
      employee: new FormControl(executeurRawValue.employee),
    });
  }

  getExecuteur(form: ExecuteurFormGroup): IExecuteur | NewExecuteur {
    return form.getRawValue() as IExecuteur | NewExecuteur;
  }

  resetForm(form: ExecuteurFormGroup, executeur: ExecuteurFormGroupInput): void {
    const executeurRawValue = { ...this.getFormDefaults(), ...executeur };
    form.reset(
      {
        ...executeurRawValue,
        id: { value: executeurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ExecuteurFormDefaults {
    return {
      id: null,
      disponibilite: false,
    };
  }
}
