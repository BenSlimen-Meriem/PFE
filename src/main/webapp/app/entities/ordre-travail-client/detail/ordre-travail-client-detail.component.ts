import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IOrdreTravailClient } from '../ordre-travail-client.model';

@Component({
  selector: 'jhi-ordre-travail-client-detail',
  templateUrl: './ordre-travail-client-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class OrdreTravailClientDetailComponent {
  ordreTravailClient = input<IOrdreTravailClient | null>(null);

  previousState(): void {
    window.history.back();
  }
}
