import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOrdreTravailClient } from '../ordre-travail-client.model';
import { OrdreTravailClientService } from '../service/ordre-travail-client.service';

@Component({
  templateUrl: './ordre-travail-client-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OrdreTravailClientDeleteDialogComponent {
  ordreTravailClient?: IOrdreTravailClient;

  protected ordreTravailClientService = inject(OrdreTravailClientService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ordreTravailClientService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
