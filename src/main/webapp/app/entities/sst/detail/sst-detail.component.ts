import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ISST } from '../sst.model';

@Component({
  selector: 'jhi-sst-detail',
  templateUrl: './sst-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class SSTDetailComponent {
  sST = input<ISST | null>(null);

  previousState(): void {
    window.history.back();
  }
}
