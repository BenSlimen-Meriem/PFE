import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IExecuteur } from '../executeur.model';

@Component({
  selector: 'jhi-executeur-detail',
  templateUrl: './executeur-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class ExecuteurDetailComponent {
  executeur = input<IExecuteur | null>(null);

  previousState(): void {
    window.history.back();
  }
}
