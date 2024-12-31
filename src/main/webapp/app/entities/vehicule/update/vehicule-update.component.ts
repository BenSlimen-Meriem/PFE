import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IWorkOrder } from 'app/entities/work-order/work-order.model';
import { WorkOrderService } from 'app/entities/work-order/service/work-order.service';
import { StatutVehicule } from 'app/entities/enumerations/statut-vehicule.model';
import { VehiculeService } from '../service/vehicule.service';
import { IVehicule } from '../vehicule.model';
import { VehiculeFormGroup, VehiculeFormService } from './vehicule-form.service';

@Component({
  selector: 'jhi-vehicule-update',
  templateUrl: './vehicule-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VehiculeUpdateComponent implements OnInit {
  isSaving = false;
  vehicule: IVehicule | null = null;
  statutVehiculeValues = Object.keys(StatutVehicule);

  workOrdersSharedCollection: IWorkOrder[] = [];

  protected vehiculeService = inject(VehiculeService);
  protected vehiculeFormService = inject(VehiculeFormService);
  protected workOrderService = inject(WorkOrderService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: VehiculeFormGroup = this.vehiculeFormService.createVehiculeFormGroup();

  compareWorkOrder = (o1: IWorkOrder | null, o2: IWorkOrder | null): boolean => this.workOrderService.compareWorkOrder(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicule }) => {
      this.vehicule = vehicule;
      if (vehicule) {
        this.updateForm(vehicule);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicule = this.vehiculeFormService.getVehicule(this.editForm);
    if (vehicule.id !== null) {
      this.subscribeToSaveResponse(this.vehiculeService.update(vehicule));
    } else {
      this.subscribeToSaveResponse(this.vehiculeService.create(vehicule));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicule>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(vehicule: IVehicule): void {
    this.vehicule = vehicule;
    this.vehiculeFormService.resetForm(this.editForm, vehicule);

    this.workOrdersSharedCollection = this.workOrderService.addWorkOrderToCollectionIfMissing<IWorkOrder>(
      this.workOrdersSharedCollection,
      ...(vehicule.workOrders ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.workOrderService
      .query()
      .pipe(map((res: HttpResponse<IWorkOrder[]>) => res.body ?? []))
      .pipe(
        map((workOrders: IWorkOrder[]) =>
          this.workOrderService.addWorkOrderToCollectionIfMissing<IWorkOrder>(workOrders, ...(this.vehicule?.workOrders ?? [])),
        ),
      )
      .subscribe((workOrders: IWorkOrder[]) => (this.workOrdersSharedCollection = workOrders));
  }
}
