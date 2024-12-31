import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISociete } from '../societe.model';
import { SocieteService } from '../service/societe.service';

@Component({
  templateUrl: './societe-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SocieteDeleteDialogComponent {
  societe?: ISociete;

  protected societeService = inject(SocieteService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.societeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
