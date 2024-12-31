import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISociete, NewSociete } from '../societe.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISociete for edit and NewSocieteFormGroupInput for create.
 */
type SocieteFormGroupInput = ISociete | PartialWithRequiredKeyOf<NewSociete>;

type SocieteFormDefaults = Pick<NewSociete, 'id'>;

type SocieteFormGroupContent = {
  id: FormControl<ISociete['id'] | NewSociete['id']>;
  raisonSociale: FormControl<ISociete['raisonSociale']>;
  identifiantUnique: FormControl<ISociete['identifiantUnique']>;
  registreCommerce: FormControl<ISociete['registreCommerce']>;
  codeArticle: FormControl<ISociete['codeArticle']>;
  adresse: FormControl<ISociete['adresse']>;
  codePostal: FormControl<ISociete['codePostal']>;
  pays: FormControl<ISociete['pays']>;
  telephone: FormControl<ISociete['telephone']>;
  fax: FormControl<ISociete['fax']>;
  email: FormControl<ISociete['email']>;
  agence: FormControl<ISociete['agence']>;
};

export type SocieteFormGroup = FormGroup<SocieteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocieteFormService {
  createSocieteFormGroup(societe: SocieteFormGroupInput = { id: null }): SocieteFormGroup {
    const societeRawValue = {
      ...this.getFormDefaults(),
      ...societe,
    };
    return new FormGroup<SocieteFormGroupContent>({
      id: new FormControl(
        { value: societeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      raisonSociale: new FormControl(societeRawValue.raisonSociale, {
        validators: [Validators.required],
      }),
      identifiantUnique: new FormControl(societeRawValue.identifiantUnique, {
        validators: [Validators.required],
      }),
      registreCommerce: new FormControl(societeRawValue.registreCommerce),
      codeArticle: new FormControl(societeRawValue.codeArticle),
      adresse: new FormControl(societeRawValue.adresse),
      codePostal: new FormControl(societeRawValue.codePostal),
      pays: new FormControl(societeRawValue.pays),
      telephone: new FormControl(societeRawValue.telephone),
      fax: new FormControl(societeRawValue.fax),
      email: new FormControl(societeRawValue.email),
      agence: new FormControl(societeRawValue.agence),
    });
  }

  getSociete(form: SocieteFormGroup): ISociete | NewSociete {
    return form.getRawValue() as ISociete | NewSociete;
  }

  resetForm(form: SocieteFormGroup, societe: SocieteFormGroupInput): void {
    const societeRawValue = { ...this.getFormDefaults(), ...societe };
    form.reset(
      {
        ...societeRawValue,
        id: { value: societeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SocieteFormDefaults {
    return {
      id: null,
    };
  }
}
