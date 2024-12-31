import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IGestionCout } from '../gestion-cout.model';

@Component({
  selector: 'jhi-gestion-cout-detail',
  templateUrl: './gestion-cout-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class GestionCoutDetailComponent {
  gestionCout = input<IGestionCout | null>(null);

  previousState(): void {
    window.history.back();
  }
}
