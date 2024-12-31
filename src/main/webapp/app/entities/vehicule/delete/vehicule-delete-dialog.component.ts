import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IVehicule } from '../vehicule.model';
import { VehiculeService } from '../service/vehicule.service';

@Component({
  templateUrl: './vehicule-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VehiculeDeleteDialogComponent {
  vehicule?: IVehicule;

  protected vehiculeService = inject(VehiculeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehiculeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
