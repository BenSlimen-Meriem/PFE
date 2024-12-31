import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMotif } from '../motif.model';
import { MotifService } from '../service/motif.service';

@Component({
  templateUrl: './motif-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MotifDeleteDialogComponent {
  motif?: IMotif;

  protected motifService = inject(MotifService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.motifService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
