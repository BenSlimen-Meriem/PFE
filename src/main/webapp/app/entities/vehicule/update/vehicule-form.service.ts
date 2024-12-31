import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IVehicule, NewVehicule } from '../vehicule.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVehicule for edit and NewVehiculeFormGroupInput for create.
 */
type VehiculeFormGroupInput = IVehicule | PartialWithRequiredKeyOf<NewVehicule>;

type VehiculeFormDefaults = Pick<NewVehicule, 'id' | 'workOrders'>;

type VehiculeFormGroupContent = {
  id: FormControl<IVehicule['id'] | NewVehicule['id']>;
  model: FormControl<IVehicule['model']>;
  numeroCarteGrise: FormControl<IVehicule['numeroCarteGrise']>;
  numSerie: FormControl<IVehicule['numSerie']>;
  statut: FormControl<IVehicule['statut']>;
  type: FormControl<IVehicule['type']>;
  workOrders: FormControl<IVehicule['workOrders']>;
};

export type VehiculeFormGroup = FormGroup<VehiculeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VehiculeFormService {
  createVehiculeFormGroup(vehicule: VehiculeFormGroupInput = { id: null }): VehiculeFormGroup {
    const vehiculeRawValue = {
      ...this.getFormDefaults(),
      ...vehicule,
    };
    return new FormGroup<VehiculeFormGroupContent>({
      id: new FormControl(
        { value: vehiculeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      model: new FormControl(vehiculeRawValue.model, {
        validators: [Validators.required],
      }),
      numeroCarteGrise: new FormControl(vehiculeRawValue.numeroCarteGrise, {
        validators: [Validators.required],
      }),
      numSerie: new FormControl(vehiculeRawValue.numSerie, {
        validators: [Validators.required],
      }),
      statut: new FormControl(vehiculeRawValue.statut, {
        validators: [Validators.required],
      }),
      type: new FormControl(vehiculeRawValue.type, {
        validators: [Validators.required],
      }),
      workOrders: new FormControl(vehiculeRawValue.workOrders ?? []),
    });
  }

  getVehicule(form: VehiculeFormGroup): IVehicule | NewVehicule {
    return form.getRawValue() as IVehicule | NewVehicule;
  }

  resetForm(form: VehiculeFormGroup, vehicule: VehiculeFormGroupInput): void {
    const vehiculeRawValue = { ...this.getFormDefaults(), ...vehicule };
    form.reset(
      {
        ...vehiculeRawValue,
        id: { value: vehiculeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VehiculeFormDefaults {
    return {
      id: null,
      workOrders: [],
    };
  }
}
