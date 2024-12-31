import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAffaire, NewAffaire } from '../affaire.model';

export type PartialUpdateAffaire = Partial<IAffaire> & Pick<IAffaire, 'id'>;

export type EntityResponseType = HttpResponse<IAffaire>;
export type EntityArrayResponseType = HttpResponse<IAffaire[]>;

@Injectable({ providedIn: 'root' })
export class AffaireService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/affaires');

  create(affaire: NewAffaire): Observable<EntityResponseType> {
    return this.http.post<IAffaire>(this.resourceUrl, affaire, { observe: 'response' });
  }

  update(affaire: IAffaire): Observable<EntityResponseType> {
    return this.http.put<IAffaire>(`${this.resourceUrl}/${this.getAffaireIdentifier(affaire)}`, affaire, { observe: 'response' });
  }

  partialUpdate(affaire: PartialUpdateAffaire): Observable<EntityResponseType> {
    return this.http.patch<IAffaire>(`${this.resourceUrl}/${this.getAffaireIdentifier(affaire)}`, affaire, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAffaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAffaire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAffaireIdentifier(affaire: Pick<IAffaire, 'id'>): number {
    return affaire.id;
  }

  compareAffaire(o1: Pick<IAffaire, 'id'> | null, o2: Pick<IAffaire, 'id'> | null): boolean {
    return o1 && o2 ? this.getAffaireIdentifier(o1) === this.getAffaireIdentifier(o2) : o1 === o2;
  }

  addAffaireToCollectionIfMissing<Type extends Pick<IAffaire, 'id'>>(
    affaireCollection: Type[],
    ...affairesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const affaires: Type[] = affairesToCheck.filter(isPresent);
    if (affaires.length > 0) {
      const affaireCollectionIdentifiers = affaireCollection.map(affaireItem => this.getAffaireIdentifier(affaireItem));
      const affairesToAdd = affaires.filter(affaireItem => {
        const affaireIdentifier = this.getAffaireIdentifier(affaireItem);
        if (affaireCollectionIdentifiers.includes(affaireIdentifier)) {
          return false;
        }
        affaireCollectionIdentifiers.push(affaireIdentifier);
        return true;
      });
      return [...affairesToAdd, ...affaireCollection];
    }
    return affaireCollection;
  }
}
