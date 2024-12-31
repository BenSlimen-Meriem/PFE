import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IMotif } from '../motif.model';

@Component({
  selector: 'jhi-motif-detail',
  templateUrl: './motif-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class MotifDetailComponent {
  motif = input<IMotif | null>(null);

  previousState(): void {
    window.history.back();
  }
}
