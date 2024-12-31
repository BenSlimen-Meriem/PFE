import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IWorkOrder } from 'app/entities/work-order/work-order.model';
import { WorkOrderService } from 'app/entities/work-order/service/work-order.service';
import { IOrdreTravailClient } from '../ordre-travail-client.model';
import { OrdreTravailClientService } from '../service/ordre-travail-client.service';
import { OrdreTravailClientFormGroup, OrdreTravailClientFormService } from './ordre-travail-client-form.service';

@Component({
  selector: 'jhi-ordre-travail-client-update',
  templateUrl: './ordre-travail-client-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrdreTravailClientUpdateComponent implements OnInit {
  isSaving = false;
  ordreTravailClient: IOrdreTravailClient | null = null;

  workOrdersSharedCollection: IWorkOrder[] = [];

  protected ordreTravailClientService = inject(OrdreTravailClientService);
  protected ordreTravailClientFormService = inject(OrdreTravailClientFormService);
  protected workOrderService = inject(WorkOrderService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OrdreTravailClientFormGroup = this.ordreTravailClientFormService.createOrdreTravailClientFormGroup();

  compareWorkOrder = (o1: IWorkOrder | null, o2: IWorkOrder | null): boolean => this.workOrderService.compareWorkOrder(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ordreTravailClient }) => {
      this.ordreTravailClient = ordreTravailClient;
      if (ordreTravailClient) {
        this.updateForm(ordreTravailClient);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ordreTravailClient = this.ordreTravailClientFormService.getOrdreTravailClient(this.editForm);
    if (ordreTravailClient.id !== null) {
      this.subscribeToSaveResponse(this.ordreTravailClientService.update(ordreTravailClient));
    } else {
      this.subscribeToSaveResponse(this.ordreTravailClientService.create(ordreTravailClient));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrdreTravailClient>>): void {
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

  protected updateForm(ordreTravailClient: IOrdreTravailClient): void {
    this.ordreTravailClient = ordreTravailClient;
    this.ordreTravailClientFormService.resetForm(this.editForm, ordreTravailClient);

    this.workOrdersSharedCollection = this.workOrderService.addWorkOrderToCollectionIfMissing<IWorkOrder>(
      this.workOrdersSharedCollection,
      ordreTravailClient.workOrder,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.workOrderService
      .query()
      .pipe(map((res: HttpResponse<IWorkOrder[]>) => res.body ?? []))
      .pipe(
        map((workOrders: IWorkOrder[]) =>
          this.workOrderService.addWorkOrderToCollectionIfMissing<IWorkOrder>(workOrders, this.ordreTravailClient?.workOrder),
        ),
      )
      .subscribe((workOrders: IWorkOrder[]) => (this.workOrdersSharedCollection = workOrders));
  }
}
