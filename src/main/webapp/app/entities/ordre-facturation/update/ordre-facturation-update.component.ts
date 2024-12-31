import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IWorkOrder } from 'app/entities/work-order/work-order.model';
import { WorkOrderService } from 'app/entities/work-order/service/work-order.service';
import { IOrdreFacturation } from '../ordre-facturation.model';
import { OrdreFacturationService } from '../service/ordre-facturation.service';
import { OrdreFacturationFormGroup, OrdreFacturationFormService } from './ordre-facturation-form.service';

@Component({
  selector: 'jhi-ordre-facturation-update',
  templateUrl: './ordre-facturation-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrdreFacturationUpdateComponent implements OnInit {
  isSaving = false;
  ordreFacturation: IOrdreFacturation | null = null;

  workOrdersSharedCollection: IWorkOrder[] = [];

  protected ordreFacturationService = inject(OrdreFacturationService);
  protected ordreFacturationFormService = inject(OrdreFacturationFormService);
  protected workOrderService = inject(WorkOrderService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OrdreFacturationFormGroup = this.ordreFacturationFormService.createOrdreFacturationFormGroup();

  compareWorkOrder = (o1: IWorkOrder | null, o2: IWorkOrder | null): boolean => this.workOrderService.compareWorkOrder(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ordreFacturation }) => {
      this.ordreFacturation = ordreFacturation;
      if (ordreFacturation) {
        this.updateForm(ordreFacturation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ordreFacturation = this.ordreFacturationFormService.getOrdreFacturation(this.editForm);
    if (ordreFacturation.id !== null) {
      this.subscribeToSaveResponse(this.ordreFacturationService.update(ordreFacturation));
    } else {
      this.subscribeToSaveResponse(this.ordreFacturationService.create(ordreFacturation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrdreFacturation>>): void {
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

  protected updateForm(ordreFacturation: IOrdreFacturation): void {
    this.ordreFacturation = ordreFacturation;
    this.ordreFacturationFormService.resetForm(this.editForm, ordreFacturation);

    this.workOrdersSharedCollection = this.workOrderService.addWorkOrderToCollectionIfMissing<IWorkOrder>(
      this.workOrdersSharedCollection,
      ordreFacturation.workOrder,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.workOrderService
      .query()
      .pipe(map((res: HttpResponse<IWorkOrder[]>) => res.body ?? []))
      .pipe(
        map((workOrders: IWorkOrder[]) =>
          this.workOrderService.addWorkOrderToCollectionIfMissing<IWorkOrder>(workOrders, this.ordreFacturation?.workOrder),
        ),
      )
      .subscribe((workOrders: IWorkOrder[]) => (this.workOrdersSharedCollection = workOrders));
  }
}
