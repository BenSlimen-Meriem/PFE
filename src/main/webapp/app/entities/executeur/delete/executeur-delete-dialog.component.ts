import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IExecuteur } from '../executeur.model';
import { ExecuteurService } from '../service/executeur.service';

@Component({
  templateUrl: './executeur-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ExecuteurDeleteDialogComponent {
  executeur?: IExecuteur;

  protected executeurService = inject(ExecuteurService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.executeurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
