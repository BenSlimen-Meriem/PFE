import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IExecuteur } from '../executeur.model';
import { ExecuteurService } from '../service/executeur.service';
import { ExecuteurFormGroup, ExecuteurFormService } from './executeur-form.service';

@Component({
  selector: 'jhi-executeur-update',
  templateUrl: './executeur-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ExecuteurUpdateComponent implements OnInit {
  isSaving = false;
  executeur: IExecuteur | null = null;

  employeesCollection: IEmployee[] = [];

  protected executeurService = inject(ExecuteurService);
  protected executeurFormService = inject(ExecuteurFormService);
  protected employeeService = inject(EmployeeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ExecuteurFormGroup = this.executeurFormService.createExecuteurFormGroup();

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ executeur }) => {
      this.executeur = executeur;
      if (executeur) {
        this.updateForm(executeur);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const executeur = this.executeurFormService.getExecuteur(this.editForm);
    if (executeur.id !== null) {
      this.subscribeToSaveResponse(this.executeurService.update(executeur));
    } else {
      this.subscribeToSaveResponse(this.executeurService.create(executeur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExecuteur>>): void {
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

  protected updateForm(executeur: IExecuteur): void {
    this.executeur = executeur;
    this.executeurFormService.resetForm(this.editForm, executeur);

    this.employeesCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesCollection,
      executeur.employee,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query({ filter: 'executeur-is-null' })
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.executeur?.employee),
        ),
      )
      .subscribe((employees: IEmployee[]) => (this.employeesCollection = employees));
  }
}
