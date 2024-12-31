import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrdreTravailClient, NewOrdreTravailClient } from '../ordre-travail-client.model';

export type PartialUpdateOrdreTravailClient = Partial<IOrdreTravailClient> & Pick<IOrdreTravailClient, 'id'>;

export type EntityResponseType = HttpResponse<IOrdreTravailClient>;
export type EntityArrayResponseType = HttpResponse<IOrdreTravailClient[]>;

@Injectable({ providedIn: 'root' })
export class OrdreTravailClientService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ordre-travail-clients');

  create(ordreTravailClient: NewOrdreTravailClient): Observable<EntityResponseType> {
    return this.http.post<IOrdreTravailClient>(this.resourceUrl, ordreTravailClient, { observe: 'response' });
  }

  update(ordreTravailClient: IOrdreTravailClient): Observable<EntityResponseType> {
    return this.http.put<IOrdreTravailClient>(
      `${this.resourceUrl}/${this.getOrdreTravailClientIdentifier(ordreTravailClient)}`,
      ordreTravailClient,
      { observe: 'response' },
    );
  }

  partialUpdate(ordreTravailClient: PartialUpdateOrdreTravailClient): Observable<EntityResponseType> {
    return this.http.patch<IOrdreTravailClient>(
      `${this.resourceUrl}/${this.getOrdreTravailClientIdentifier(ordreTravailClient)}`,
      ordreTravailClient,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrdreTravailClient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrdreTravailClient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrdreTravailClientIdentifier(ordreTravailClient: Pick<IOrdreTravailClient, 'id'>): number {
    return ordreTravailClient.id;
  }

  compareOrdreTravailClient(o1: Pick<IOrdreTravailClient, 'id'> | null, o2: Pick<IOrdreTravailClient, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrdreTravailClientIdentifier(o1) === this.getOrdreTravailClientIdentifier(o2) : o1 === o2;
  }

  addOrdreTravailClientToCollectionIfMissing<Type extends Pick<IOrdreTravailClient, 'id'>>(
    ordreTravailClientCollection: Type[],
    ...ordreTravailClientsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ordreTravailClients: Type[] = ordreTravailClientsToCheck.filter(isPresent);
    if (ordreTravailClients.length > 0) {
      const ordreTravailClientCollectionIdentifiers = ordreTravailClientCollection.map(ordreTravailClientItem =>
        this.getOrdreTravailClientIdentifier(ordreTravailClientItem),
      );
      const ordreTravailClientsToAdd = ordreTravailClients.filter(ordreTravailClientItem => {
        const ordreTravailClientIdentifier = this.getOrdreTravailClientIdentifier(ordreTravailClientItem);
        if (ordreTravailClientCollectionIdentifiers.includes(ordreTravailClientIdentifier)) {
          return false;
        }
        ordreTravailClientCollectionIdentifiers.push(ordreTravailClientIdentifier);
        return true;
      });
      return [...ordreTravailClientsToAdd, ...ordreTravailClientCollection];
    }
    return ordreTravailClientCollection;
  }
}
