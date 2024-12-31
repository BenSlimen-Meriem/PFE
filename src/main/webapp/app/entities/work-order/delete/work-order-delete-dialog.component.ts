import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IWorkOrder } from '../work-order.model';
import { WorkOrderService } from '../service/work-order.service';

@Component({
  templateUrl: './work-order-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class WorkOrderDeleteDialogComponent {
  workOrder?: IWorkOrder;

  protected workOrderService = inject(WorkOrderService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workOrderService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
