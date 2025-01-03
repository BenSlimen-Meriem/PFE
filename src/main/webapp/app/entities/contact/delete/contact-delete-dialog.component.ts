import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContact } from '../contact.model';
import { ContactService } from '../service/contact.service';

@Component({
  templateUrl: './contact-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContactDeleteDialogComponent {
  contact?: IContact;

  protected contactService = inject(ContactService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
