import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlanificateur, NewPlanificateur } from '../planificateur.model';

export type PartialUpdatePlanificateur = Partial<IPlanificateur> & Pick<IPlanificateur, 'id'>;

export type EntityResponseType = HttpResponse<IPlanificateur>;
export type EntityArrayResponseType = HttpResponse<IPlanificateur[]>;

@Injectable({ providedIn: 'root' })
export class PlanificateurService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/planificateurs');

  create(planificateur: NewPlanificateur): Observable<EntityResponseType> {
    return this.http.post<IPlanificateur>(this.resourceUrl, planificateur, { observe: 'response' });
  }

  update(planificateur: IPlanificateur): Observable<EntityResponseType> {
    return this.http.put<IPlanificateur>(`${this.resourceUrl}/${this.getPlanificateurIdentifier(planificateur)}`, planificateur, {
      observe: 'response',
    });
  }

  partialUpdate(planificateur: PartialUpdatePlanificateur): Observable<EntityResponseType> {
    return this.http.patch<IPlanificateur>(`${this.resourceUrl}/${this.getPlanificateurIdentifier(planificateur)}`, planificateur, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanificateur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanificateur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlanificateurIdentifier(planificateur: Pick<IPlanificateur, 'id'>): number {
    return planificateur.id;
  }

  comparePlanificateur(o1: Pick<IPlanificateur, 'id'> | null, o2: Pick<IPlanificateur, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlanificateurIdentifier(o1) === this.getPlanificateurIdentifier(o2) : o1 === o2;
  }

  addPlanificateurToCollectionIfMissing<Type extends Pick<IPlanificateur, 'id'>>(
    planificateurCollection: Type[],
    ...planificateursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const planificateurs: Type[] = planificateursToCheck.filter(isPresent);
    if (planificateurs.length > 0) {
      const planificateurCollectionIdentifiers = planificateurCollection.map(planificateurItem =>
        this.getPlanificateurIdentifier(planificateurItem),
      );
      const planificateursToAdd = planificateurs.filter(planificateurItem => {
        const planificateurIdentifier = this.getPlanificateurIdentifier(planificateurItem);
        if (planificateurCollectionIdentifiers.includes(planificateurIdentifier)) {
          return false;
        }
        planificateurCollectionIdentifiers.push(planificateurIdentifier);
        return true;
      });
      return [...planificateursToAdd, ...planificateurCollection];
    }
    return planificateurCollection;
  }
}
