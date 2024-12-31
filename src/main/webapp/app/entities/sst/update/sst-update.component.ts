import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISST } from '../sst.model';
import { SSTService } from '../service/sst.service';
import { SSTFormGroup, SSTFormService } from './sst-form.service';

@Component({
  selector: 'jhi-sst-update',
  templateUrl: './sst-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SSTUpdateComponent implements OnInit {
  isSaving = false;
  sST: ISST | null = null;

  protected sSTService = inject(SSTService);
  protected sSTFormService = inject(SSTFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SSTFormGroup = this.sSTFormService.createSSTFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sST }) => {
      this.sST = sST;
      if (sST) {
        this.updateForm(sST);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sST = this.sSTFormService.getSST(this.editForm);
    if (sST.id !== null) {
      this.subscribeToSaveResponse(this.sSTService.update(sST));
    } else {
      this.subscribeToSaveResponse(this.sSTService.create(sST));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISST>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(sST: ISST): void {
    this.sST = sST;
    this.sSTFormService.resetForm(this.editForm, sST);
  }
}
