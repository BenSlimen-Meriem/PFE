import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGestionCout } from '../gestion-cout.model';
import { GestionCoutService } from '../service/gestion-cout.service';

@Component({
  templateUrl: './gestion-cout-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GestionCoutDeleteDialogComponent {
  gestionCout?: IGestionCout;

  protected gestionCoutService = inject(GestionCoutService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gestionCoutService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
