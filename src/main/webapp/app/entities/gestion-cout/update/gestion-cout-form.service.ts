import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IGestionCout, NewGestionCout } from '../gestion-cout.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGestionCout for edit and NewGestionCoutFormGroupInput for create.
 */
type GestionCoutFormGroupInput = IGestionCout | PartialWithRequiredKeyOf<NewGestionCout>;

type GestionCoutFormDefaults = Pick<NewGestionCout, 'id'>;

type GestionCoutFormGroupContent = {
  id: FormControl<IGestionCout['id'] | NewGestionCout['id']>;
  time: FormControl<IGestionCout['time']>;
  cout: FormControl<IGestionCout['cout']>;
  planificateur: FormControl<IGestionCout['planificateur']>;
};

export type GestionCoutFormGroup = FormGroup<GestionCoutFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GestionCoutFormService {
  createGestionCoutFormGroup(gestionCout: GestionCoutFormGroupInput = { id: null }): GestionCoutFormGroup {
    const gestionCoutRawValue = {
      ...this.getFormDefaults(),
      ...gestionCout,
    };
    return new FormGroup<GestionCoutFormGroupContent>({
      id: new FormControl(
        { value: gestionCoutRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      time: new FormControl(gestionCoutRawValue.time, {
        validators: [Validators.required],
      }),
      cout: new FormControl(gestionCoutRawValue.cout, {
        validators: [Validators.required],
      }),
      planificateur: new FormControl(gestionCoutRawValue.planificateur),
    });
  }

  getGestionCout(form: GestionCoutFormGroup): IGestionCout | NewGestionCout {
    return form.getRawValue() as IGestionCout | NewGestionCout;
  }

  resetForm(form: GestionCoutFormGroup, gestionCout: GestionCoutFormGroupInput): void {
    const gestionCoutRawValue = { ...this.getFormDefaults(), ...gestionCout };
    form.reset(
      {
        ...gestionCoutRawValue,
        id: { value: gestionCoutRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GestionCoutFormDefaults {
    return {
      id: null,
    };
  }
}
