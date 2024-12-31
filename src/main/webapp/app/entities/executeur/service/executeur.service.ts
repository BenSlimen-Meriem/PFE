import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IExecuteur, NewExecuteur } from '../executeur.model';

export type PartialUpdateExecuteur = Partial<IExecuteur> & Pick<IExecuteur, 'id'>;

export type EntityResponseType = HttpResponse<IExecuteur>;
export type EntityArrayResponseType = HttpResponse<IExecuteur[]>;

@Injectable({ providedIn: 'root' })
export class ExecuteurService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/executeurs');

  create(executeur: NewExecuteur): Observable<EntityResponseType> {
    return this.http.post<IExecuteur>(this.resourceUrl, executeur, { observe: 'response' });
  }

  update(executeur: IExecuteur): Observable<EntityResponseType> {
    return this.http.put<IExecuteur>(`${this.resourceUrl}/${this.getExecuteurIdentifier(executeur)}`, executeur, { observe: 'response' });
  }

  partialUpdate(executeur: PartialUpdateExecuteur): Observable<EntityResponseType> {
    return this.http.patch<IExecuteur>(`${this.resourceUrl}/${this.getExecuteurIdentifier(executeur)}`, executeur, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExecuteur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExecuteur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getExecuteurIdentifier(executeur: Pick<IExecuteur, 'id'>): number {
    return executeur.id;
  }

  compareExecuteur(o1: Pick<IExecuteur, 'id'> | null, o2: Pick<IExecuteur, 'id'> | null): boolean {
    return o1 && o2 ? this.getExecuteurIdentifier(o1) === this.getExecuteurIdentifier(o2) : o1 === o2;
  }

  addExecuteurToCollectionIfMissing<Type extends Pick<IExecuteur, 'id'>>(
    executeurCollection: Type[],
    ...executeursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const executeurs: Type[] = executeursToCheck.filter(isPresent);
    if (executeurs.length > 0) {
      const executeurCollectionIdentifiers = executeurCollection.map(executeurItem => this.getExecuteurIdentifier(executeurItem));
      const executeursToAdd = executeurs.filter(executeurItem => {
        const executeurIdentifier = this.getExecuteurIdentifier(executeurItem);
        if (executeurCollectionIdentifiers.includes(executeurIdentifier)) {
          return false;
        }
        executeurCollectionIdentifiers.push(executeurIdentifier);
        return true;
      });
      return [...executeursToAdd, ...executeurCollection];
    }
    return executeurCollection;
  }
}
