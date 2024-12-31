import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPlanificateur } from 'app/entities/planificateur/planificateur.model';
import { PlanificateurService } from 'app/entities/planificateur/service/planificateur.service';
import { IGestionCout } from '../gestion-cout.model';
import { GestionCoutService } from '../service/gestion-cout.service';
import { GestionCoutFormGroup, GestionCoutFormService } from './gestion-cout-form.service';

@Component({
  selector: 'jhi-gestion-cout-update',
  templateUrl: './gestion-cout-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GestionCoutUpdateComponent implements OnInit {
  isSaving = false;
  gestionCout: IGestionCout | null = null;

  planificateursCollection: IPlanificateur[] = [];

  protected gestionCoutService = inject(GestionCoutService);
  protected gestionCoutFormService = inject(GestionCoutFormService);
  protected planificateurService = inject(PlanificateurService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GestionCoutFormGroup = this.gestionCoutFormService.createGestionCoutFormGroup();

  comparePlanificateur = (o1: IPlanificateur | null, o2: IPlanificateur | null): boolean =>
    this.planificateurService.comparePlanificateur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gestionCout }) => {
      this.gestionCout = gestionCout;
      if (gestionCout) {
        this.updateForm(gestionCout);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gestionCout = this.gestionCoutFormService.getGestionCout(this.editForm);
    if (gestionCout.id !== null) {
      this.subscribeToSaveResponse(this.gestionCoutService.update(gestionCout));
    } else {
      this.subscribeToSaveResponse(this.gestionCoutService.create(gestionCout));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGestionCout>>): void {
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

  protected updateForm(gestionCout: IGestionCout): void {
    this.gestionCout = gestionCout;
    this.gestionCoutFormService.resetForm(this.editForm, gestionCout);

    this.planificateursCollection = this.planificateurService.addPlanificateurToCollectionIfMissing<IPlanificateur>(
      this.planificateursCollection,
      gestionCout.planificateur,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.planificateurService
      .query({ filter: 'gestioncout-is-null' })
      .pipe(map((res: HttpResponse<IPlanificateur[]>) => res.body ?? []))
      .pipe(
        map((planificateurs: IPlanificateur[]) =>
          this.planificateurService.addPlanificateurToCollectionIfMissing<IPlanificateur>(planificateurs, this.gestionCout?.planificateur),
        ),
      )
      .subscribe((planificateurs: IPlanificateur[]) => (this.planificateursCollection = planificateurs));
  }
}
