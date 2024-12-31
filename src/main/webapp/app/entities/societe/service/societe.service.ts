import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISociete, NewSociete } from '../societe.model';

export type PartialUpdateSociete = Partial<ISociete> & Pick<ISociete, 'id'>;

export type EntityResponseType = HttpResponse<ISociete>;
export type EntityArrayResponseType = HttpResponse<ISociete[]>;

@Injectable({ providedIn: 'root' })
export class SocieteService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/societes');

  create(societe: NewSociete): Observable<EntityResponseType> {
    return this.http.post<ISociete>(this.resourceUrl, societe, { observe: 'response' });
  }

  update(societe: ISociete): Observable<EntityResponseType> {
    return this.http.put<ISociete>(`${this.resourceUrl}/${this.getSocieteIdentifier(societe)}`, societe, { observe: 'response' });
  }

  partialUpdate(societe: PartialUpdateSociete): Observable<EntityResponseType> {
    return this.http.patch<ISociete>(`${this.resourceUrl}/${this.getSocieteIdentifier(societe)}`, societe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISociete>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISociete[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSocieteIdentifier(societe: Pick<ISociete, 'id'>): number {
    return societe.id;
  }

  compareSociete(o1: Pick<ISociete, 'id'> | null, o2: Pick<ISociete, 'id'> | null): boolean {
    return o1 && o2 ? this.getSocieteIdentifier(o1) === this.getSocieteIdentifier(o2) : o1 === o2;
  }

  addSocieteToCollectionIfMissing<Type extends Pick<ISociete, 'id'>>(
    societeCollection: Type[],
    ...societesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const societes: Type[] = societesToCheck.filter(isPresent);
    if (societes.length > 0) {
      const societeCollectionIdentifiers = societeCollection.map(societeItem => this.getSocieteIdentifier(societeItem));
      const societesToAdd = societes.filter(societeItem => {
        const societeIdentifier = this.getSocieteIdentifier(societeItem);
        if (societeCollectionIdentifiers.includes(societeIdentifier)) {
          return false;
        }
        societeCollectionIdentifiers.push(societeIdentifier);
        return true;
      });
      return [...societesToAdd, ...societeCollection];
    }
    return societeCollection;
  }
}
