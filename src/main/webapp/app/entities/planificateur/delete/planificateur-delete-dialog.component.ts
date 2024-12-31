import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPlanificateur } from '../planificateur.model';
import { PlanificateurService } from '../service/planificateur.service';

@Component({
  templateUrl: './planificateur-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PlanificateurDeleteDialogComponent {
  planificateur?: IPlanificateur;

  protected planificateurService = inject(PlanificateurService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planificateurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
