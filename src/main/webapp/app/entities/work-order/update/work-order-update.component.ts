import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAffaire } from 'app/entities/affaire/affaire.model';
import { AffaireService } from 'app/entities/affaire/service/affaire.service';
import { IMotif } from 'app/entities/motif/motif.model';
import { MotifService } from 'app/entities/motif/service/motif.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IArticle } from 'app/entities/article/article.model';
import { ArticleService } from 'app/entities/article/service/article.service';
import { IVehicule } from 'app/entities/vehicule/vehicule.model';
import { VehiculeService } from 'app/entities/vehicule/service/vehicule.service';
import { ISite } from 'app/entities/site/site.model';
import { SiteService } from 'app/entities/site/service/site.service';
import { StatutMission } from 'app/entities/enumerations/statut-mission.model';
import { WorkOrderService } from '../service/work-order.service';
import { IWorkOrder } from '../work-order.model';
import { WorkOrderFormGroup, WorkOrderFormService } from './work-order-form.service';

@Component({
  selector: 'jhi-work-order-update',
  templateUrl: './work-order-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class WorkOrderUpdateComponent implements OnInit {
  isSaving = false;
  workOrder: IWorkOrder | null = null;
  statutMissionValues = Object.keys(StatutMission);

  affairesSharedCollection: IAffaire[] = [];
  motifsSharedCollection: IMotif[] = [];
  employeesSharedCollection: IEmployee[] = [];
  articlesSharedCollection: IArticle[] = [];
  vehiculesSharedCollection: IVehicule[] = [];
  sitesSharedCollection: ISite[] = [];

  protected workOrderService = inject(WorkOrderService);
  protected workOrderFormService = inject(WorkOrderFormService);
  protected affaireService = inject(AffaireService);
  protected motifService = inject(MotifService);
  protected employeeService = inject(EmployeeService);
  protected articleService = inject(ArticleService);
  protected vehiculeService = inject(VehiculeService);
  protected siteService = inject(SiteService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: WorkOrderFormGroup = this.workOrderFormService.createWorkOrderFormGroup();

  compareAffaire = (o1: IAffaire | null, o2: IAffaire | null): boolean => this.affaireService.compareAffaire(o1, o2);

  compareMotif = (o1: IMotif | null, o2: IMotif | null): boolean => this.motifService.compareMotif(o1, o2);

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  compareArticle = (o1: IArticle | null, o2: IArticle | null): boolean => this.articleService.compareArticle(o1, o2);

  compareVehicule = (o1: IVehicule | null, o2: IVehicule | null): boolean => this.vehiculeService.compareVehicule(o1, o2);

  compareSite = (o1: ISite | null, o2: ISite | null): boolean => this.siteService.compareSite(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workOrder }) => {
      this.workOrder = workOrder;
      if (workOrder) {
        this.updateForm(workOrder);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workOrder = this.workOrderFormService.getWorkOrder(this.editForm);
    if (workOrder.id !== null) {
      this.subscribeToSaveResponse(this.workOrderService.update(workOrder));
    } else {
      this.subscribeToSaveResponse(this.workOrderService.create(workOrder));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkOrder>>): void {
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

  protected updateForm(workOrder: IWorkOrder): void {
    this.workOrder = workOrder;
    this.workOrderFormService.resetForm(this.editForm, workOrder);

    this.affairesSharedCollection = this.affaireService.addAffaireToCollectionIfMissing<IAffaire>(
      this.affairesSharedCollection,
      workOrder.affaire,
    );
    this.motifsSharedCollection = this.motifService.addMotifToCollectionIfMissing<IMotif>(this.motifsSharedCollection, workOrder.motif);
    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      ...(workOrder.employees ?? []),
    );
    this.articlesSharedCollection = this.articleService.addArticleToCollectionIfMissing<IArticle>(
      this.articlesSharedCollection,
      ...(workOrder.articles ?? []),
    );
    this.vehiculesSharedCollection = this.vehiculeService.addVehiculeToCollectionIfMissing<IVehicule>(
      this.vehiculesSharedCollection,
      ...(workOrder.vehicules ?? []),
    );
    this.sitesSharedCollection = this.siteService.addSiteToCollectionIfMissing<ISite>(this.sitesSharedCollection, workOrder.site);
  }

  protected loadRelationshipsOptions(): void {
    this.affaireService
      .query()
      .pipe(map((res: HttpResponse<IAffaire[]>) => res.body ?? []))
      .pipe(map((affaires: IAffaire[]) => this.affaireService.addAffaireToCollectionIfMissing<IAffaire>(affaires, this.workOrder?.affaire)))
      .subscribe((affaires: IAffaire[]) => (this.affairesSharedCollection = affaires));

    this.motifService
      .query()
      .pipe(map((res: HttpResponse<IMotif[]>) => res.body ?? []))
      .pipe(map((motifs: IMotif[]) => this.motifService.addMotifToCollectionIfMissing<IMotif>(motifs, this.workOrder?.motif)))
      .subscribe((motifs: IMotif[]) => (this.motifsSharedCollection = motifs));

    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, ...(this.workOrder?.employees ?? [])),
        ),
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));

    this.articleService
      .query()
      .pipe(map((res: HttpResponse<IArticle[]>) => res.body ?? []))
      .pipe(
        map((articles: IArticle[]) =>
          this.articleService.addArticleToCollectionIfMissing<IArticle>(articles, ...(this.workOrder?.articles ?? [])),
        ),
      )
      .subscribe((articles: IArticle[]) => (this.articlesSharedCollection = articles));

    this.vehiculeService
      .query()
      .pipe(map((res: HttpResponse<IVehicule[]>) => res.body ?? []))
      .pipe(
        map((vehicules: IVehicule[]) =>
          this.vehiculeService.addVehiculeToCollectionIfMissing<IVehicule>(vehicules, ...(this.workOrder?.vehicules ?? [])),
        ),
      )
      .subscribe((vehicules: IVehicule[]) => (this.vehiculesSharedCollection = vehicules));

    this.siteService
      .query()
      .pipe(map((res: HttpResponse<ISite[]>) => res.body ?? []))
      .pipe(map((sites: ISite[]) => this.siteService.addSiteToCollectionIfMissing<ISite>(sites, this.workOrder?.site)))
      .subscribe((sites: ISite[]) => (this.sitesSharedCollection = sites));
  }
}
