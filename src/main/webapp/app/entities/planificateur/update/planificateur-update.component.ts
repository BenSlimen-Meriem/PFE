import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IPlanificateur } from '../planificateur.model';
import { PlanificateurService } from '../service/planificateur.service';
import { PlanificateurFormGroup, PlanificateurFormService } from './planificateur-form.service';

@Component({
  selector: 'jhi-planificateur-update',
  templateUrl: './planificateur-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PlanificateurUpdateComponent implements OnInit {
  isSaving = false;
  planificateur: IPlanificateur | null = null;

  employeesCollection: IEmployee[] = [];

  protected planificateurService = inject(PlanificateurService);
  protected planificateurFormService = inject(PlanificateurFormService);
  protected employeeService = inject(EmployeeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PlanificateurFormGroup = this.planificateurFormService.createPlanificateurFormGroup();

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planificateur }) => {
      this.planificateur = planificateur;
      if (planificateur) {
        this.updateForm(planificateur);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planificateur = this.planificateurFormService.getPlanificateur(this.editForm);
    if (planificateur.id !== null) {
      this.subscribeToSaveResponse(this.planificateurService.update(planificateur));
    } else {
      this.subscribeToSaveResponse(this.planificateurService.create(planificateur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanificateur>>): void {
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

  protected updateForm(planificateur: IPlanificateur): void {
    this.planificateur = planificateur;
    this.planificateurFormService.resetForm(this.editForm, planificateur);

    this.employeesCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesCollection,
      planificateur.employee,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query({ filter: 'planificateur-is-null' })
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.planificateur?.employee),
        ),
      )
      .subscribe((employees: IEmployee[]) => (this.employeesCollection = employees));
  }
}
