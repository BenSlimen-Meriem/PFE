import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPlanificateur, NewPlanificateur } from '../planificateur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlanificateur for edit and NewPlanificateurFormGroupInput for create.
 */
type PlanificateurFormGroupInput = IPlanificateur | PartialWithRequiredKeyOf<NewPlanificateur>;

type PlanificateurFormDefaults = Pick<NewPlanificateur, 'id' | 'disponibilite'>;

type PlanificateurFormGroupContent = {
  id: FormControl<IPlanificateur['id'] | NewPlanificateur['id']>;
  nom: FormControl<IPlanificateur['nom']>;
  niveauExpertise: FormControl<IPlanificateur['niveauExpertise']>;
  disponibilite: FormControl<IPlanificateur['disponibilite']>;
  employee: FormControl<IPlanificateur['employee']>;
};

export type PlanificateurFormGroup = FormGroup<PlanificateurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlanificateurFormService {
  createPlanificateurFormGroup(planificateur: PlanificateurFormGroupInput = { id: null }): PlanificateurFormGroup {
    const planificateurRawValue = {
      ...this.getFormDefaults(),
      ...planificateur,
    };
    return new FormGroup<PlanificateurFormGroupContent>({
      id: new FormControl(
        { value: planificateurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(planificateurRawValue.nom, {
        validators: [Validators.required],
      }),
      niveauExpertise: new FormControl(planificateurRawValue.niveauExpertise, {
        validators: [Validators.required],
      }),
      disponibilite: new FormControl(planificateurRawValue.disponibilite, {
        validators: [Validators.required],
      }),
      employee: new FormControl(planificateurRawValue.employee),
    });
  }

  getPlanificateur(form: PlanificateurFormGroup): IPlanificateur | NewPlanificateur {
    return form.getRawValue() as IPlanificateur | NewPlanificateur;
  }

  resetForm(form: PlanificateurFormGroup, planificateur: PlanificateurFormGroupInput): void {
    const planificateurRawValue = { ...this.getFormDefaults(), ...planificateur };
    form.reset(
      {
        ...planificateurRawValue,
        id: { value: planificateurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PlanificateurFormDefaults {
    return {
      id: null,
      disponibilite: false,
    };
  }
}
