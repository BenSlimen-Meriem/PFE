import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ISociete } from '../societe.model';

@Component({
  selector: 'jhi-societe-detail',
  templateUrl: './societe-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class SocieteDetailComponent {
  societe = input<ISociete | null>(null);

  previousState(): void {
    window.history.back();
  }
}
