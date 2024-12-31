import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IOrdreFacturation } from '../ordre-facturation.model';

@Component({
  selector: 'jhi-ordre-facturation-detail',
  templateUrl: './ordre-facturation-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class OrdreFacturationDetailComponent {
  ordreFacturation = input<IOrdreFacturation | null>(null);

  previousState(): void {
    window.history.back();
  }
}
