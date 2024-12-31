import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IWorkOrder, NewWorkOrder } from '../work-order.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWorkOrder for edit and NewWorkOrderFormGroupInput for create.
 */
type WorkOrderFormGroupInput = IWorkOrder | PartialWithRequiredKeyOf<NewWorkOrder>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IWorkOrder | NewWorkOrder> = Omit<T, 'dateHeureDebutPrevisionnelle' | 'dateHeureFinPrevisionnelle'> & {
  dateHeureDebutPrevisionnelle?: string | null;
  dateHeureFinPrevisionnelle?: string | null;
};

type WorkOrderFormRawValue = FormValueOf<IWorkOrder>;

type NewWorkOrderFormRawValue = FormValueOf<NewWorkOrder>;

type WorkOrderFormDefaults = Pick<
  NewWorkOrder,
  'id' | 'dateHeureDebutPrevisionnelle' | 'dateHeureFinPrevisionnelle' | 'employees' | 'articles' | 'vehicules'
>;

type WorkOrderFormGroupContent = {
  id: FormControl<WorkOrderFormRawValue['id'] | NewWorkOrder['id']>;
  demandeur: FormControl<WorkOrderFormRawValue['demandeur']>;
  nature: FormControl<WorkOrderFormRawValue['nature']>;
  motifDescription: FormControl<WorkOrderFormRawValue['motifDescription']>;
  dateHeureDebutPrevisionnelle: FormControl<WorkOrderFormRawValue['dateHeureDebutPrevisionnelle']>;
  dateHeureFinPrevisionnelle: FormControl<WorkOrderFormRawValue['dateHeureFinPrevisionnelle']>;
  vehicule: FormControl<WorkOrderFormRawValue['vehicule']>;
  materielUtilise: FormControl<WorkOrderFormRawValue['materielUtilise']>;
  article: FormControl<WorkOrderFormRawValue['article']>;
  membreMission: FormControl<WorkOrderFormRawValue['membreMission']>;
  responsableMission: FormControl<WorkOrderFormRawValue['responsableMission']>;
  statut: FormControl<WorkOrderFormRawValue['statut']>;
  affaire: FormControl<WorkOrderFormRawValue['affaire']>;
  motif: FormControl<WorkOrderFormRawValue['motif']>;
  employees: FormControl<WorkOrderFormRawValue['employees']>;
  articles: FormControl<WorkOrderFormRawValue['articles']>;
  vehicules: FormControl<WorkOrderFormRawValue['vehicules']>;
  site: FormControl<WorkOrderFormRawValue['site']>;
};

export type WorkOrderFormGroup = FormGroup<WorkOrderFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WorkOrderFormService {
  createWorkOrderFormGroup(workOrder: WorkOrderFormGroupInput = { id: null }): WorkOrderFormGroup {
    const workOrderRawValue = this.convertWorkOrderToWorkOrderRawValue({
      ...this.getFormDefaults(),
      ...workOrder,
    });
    return new FormGroup<WorkOrderFormGroupContent>({
      id: new FormControl(
        { value: workOrderRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      demandeur: new FormControl(workOrderRawValue.demandeur, {
        validators: [Validators.required],
      }),
      nature: new FormControl(workOrderRawValue.nature, {
        validators: [Validators.required],
      }),
      motifDescription: new FormControl(workOrderRawValue.motifDescription),
      dateHeureDebutPrevisionnelle: new FormControl(workOrderRawValue.dateHeureDebutPrevisionnelle, {
        validators: [Validators.required],
      }),
      dateHeureFinPrevisionnelle: new FormControl(workOrderRawValue.dateHeureFinPrevisionnelle, {
        validators: [Validators.required],
      }),
      vehicule: new FormControl(workOrderRawValue.vehicule),
      materielUtilise: new FormControl(workOrderRawValue.materielUtilise),
      article: new FormControl(workOrderRawValue.article),
      membreMission: new FormControl(workOrderRawValue.membreMission),
      responsableMission: new FormControl(workOrderRawValue.responsableMission),
      statut: new FormControl(workOrderRawValue.statut, {
        validators: [Validators.required],
      }),
      affaire: new FormControl(workOrderRawValue.affaire),
      motif: new FormControl(workOrderRawValue.motif),
      employees: new FormControl(workOrderRawValue.employees ?? []),
      articles: new FormControl(workOrderRawValue.articles ?? []),
      vehicules: new FormControl(workOrderRawValue.vehicules ?? []),
      site: new FormControl(workOrderRawValue.site),
    });
  }

  getWorkOrder(form: WorkOrderFormGroup): IWorkOrder | NewWorkOrder {
    return this.convertWorkOrderRawValueToWorkOrder(form.getRawValue() as WorkOrderFormRawValue | NewWorkOrderFormRawValue);
  }

  resetForm(form: WorkOrderFormGroup, workOrder: WorkOrderFormGroupInput): void {
    const workOrderRawValue = this.convertWorkOrderToWorkOrderRawValue({ ...this.getFormDefaults(), ...workOrder });
    form.reset(
      {
        ...workOrderRawValue,
        id: { value: workOrderRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): WorkOrderFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateHeureDebutPrevisionnelle: currentTime,
      dateHeureFinPrevisionnelle: currentTime,
      employees: [],
      articles: [],
      vehicules: [],
    };
  }

  private convertWorkOrderRawValueToWorkOrder(rawWorkOrder: WorkOrderFormRawValue | NewWorkOrderFormRawValue): IWorkOrder | NewWorkOrder {
    return {
      ...rawWorkOrder,
      dateHeureDebutPrevisionnelle: dayjs(rawWorkOrder.dateHeureDebutPrevisionnelle, DATE_TIME_FORMAT),
      dateHeureFinPrevisionnelle: dayjs(rawWorkOrder.dateHeureFinPrevisionnelle, DATE_TIME_FORMAT),
    };
  }

  private convertWorkOrderToWorkOrderRawValue(
    workOrder: IWorkOrder | (Partial<NewWorkOrder> & WorkOrderFormDefaults),
  ): WorkOrderFormRawValue | PartialWithRequiredKeyOf<NewWorkOrderFormRawValue> {
    return {
      ...workOrder,
      dateHeureDebutPrevisionnelle: workOrder.dateHeureDebutPrevisionnelle
        ? workOrder.dateHeureDebutPrevisionnelle.format(DATE_TIME_FORMAT)
        : undefined,
      dateHeureFinPrevisionnelle: workOrder.dateHeureFinPrevisionnelle
        ? workOrder.dateHeureFinPrevisionnelle.format(DATE_TIME_FORMAT)
        : undefined,
      employees: workOrder.employees ?? [],
      articles: workOrder.articles ?? [],
      vehicules: workOrder.vehicules ?? [],
    };
  }
}
