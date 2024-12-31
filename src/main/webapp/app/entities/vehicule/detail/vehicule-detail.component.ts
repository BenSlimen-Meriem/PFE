import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IVehicule } from '../vehicule.model';

@Component({
  selector: 'jhi-vehicule-detail',
  templateUrl: './vehicule-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class VehiculeDetailComponent {
  vehicule = input<IVehicule | null>(null);

  previousState(): void {
    window.history.back();
  }
}
