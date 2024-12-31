import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOrdreFacturation } from '../ordre-facturation.model';
import { OrdreFacturationService } from '../service/ordre-facturation.service';

@Component({
  templateUrl: './ordre-facturation-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OrdreFacturationDeleteDialogComponent {
  ordreFacturation?: IOrdreFacturation;

  protected ordreFacturationService = inject(OrdreFacturationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ordreFacturationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
