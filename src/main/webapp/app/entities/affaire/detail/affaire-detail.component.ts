import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IAffaire } from '../affaire.model';

@Component({
  selector: 'jhi-affaire-detail',
  templateUrl: './affaire-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class AffaireDetailComponent {
  affaire = input<IAffaire | null>(null);

  previousState(): void {
    window.history.back();
  }
}
