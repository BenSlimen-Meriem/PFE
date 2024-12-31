import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IPlanificateur } from '../planificateur.model';

@Component({
  selector: 'jhi-planificateur-detail',
  templateUrl: './planificateur-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class PlanificateurDetailComponent {
  planificateur = input<IPlanificateur | null>(null);

  previousState(): void {
    window.history.back();
  }
}
