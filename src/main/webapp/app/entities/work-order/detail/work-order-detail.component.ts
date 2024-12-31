import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IWorkOrder } from '../work-order.model';

@Component({
  selector: 'jhi-work-order-detail',
  templateUrl: './work-order-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class WorkOrderDetailComponent {
  workOrder = input<IWorkOrder | null>(null);

  previousState(): void {
    window.history.back();
  }
}
