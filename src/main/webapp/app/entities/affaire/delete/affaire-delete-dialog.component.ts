import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAffaire } from '../affaire.model';
import { AffaireService } from '../service/affaire.service';

@Component({
  templateUrl: './affaire-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AffaireDeleteDialogComponent {
  affaire?: IAffaire;

  protected affaireService = inject(AffaireService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.affaireService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
