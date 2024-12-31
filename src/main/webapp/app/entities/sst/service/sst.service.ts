import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISST, NewSST } from '../sst.model';

export type PartialUpdateSST = Partial<ISST> & Pick<ISST, 'id'>;

export type EntityResponseType = HttpResponse<ISST>;
export type EntityArrayResponseType = HttpResponse<ISST[]>;

@Injectable({ providedIn: 'root' })
export class SSTService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ssts');

  create(sST: NewSST): Observable<EntityResponseType> {
    return this.http.post<ISST>(this.resourceUrl, sST, { observe: 'response' });
  }

  update(sST: ISST): Observable<EntityResponseType> {
    return this.http.put<ISST>(`${this.resourceUrl}/${this.getSSTIdentifier(sST)}`, sST, { observe: 'response' });
  }

  partialUpdate(sST: PartialUpdateSST): Observable<EntityResponseType> {
    return this.http.patch<ISST>(`${this.resourceUrl}/${this.getSSTIdentifier(sST)}`, sST, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISST>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISST[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSSTIdentifier(sST: Pick<ISST, 'id'>): number {
    return sST.id;
  }

  compareSST(o1: Pick<ISST, 'id'> | null, o2: Pick<ISST, 'id'> | null): boolean {
    return o1 && o2 ? this.getSSTIdentifier(o1) === this.getSSTIdentifier(o2) : o1 === o2;
  }

  addSSTToCollectionIfMissing<Type extends Pick<ISST, 'id'>>(sSTCollection: Type[], ...sSTSToCheck: (Type | null | undefined)[]): Type[] {
    const sSTS: Type[] = sSTSToCheck.filter(isPresent);
    if (sSTS.length > 0) {
      const sSTCollectionIdentifiers = sSTCollection.map(sSTItem => this.getSSTIdentifier(sSTItem));
      const sSTSToAdd = sSTS.filter(sSTItem => {
        const sSTIdentifier = this.getSSTIdentifier(sSTItem);
        if (sSTCollectionIdentifiers.includes(sSTIdentifier)) {
          return false;
        }
        sSTCollectionIdentifiers.push(sSTIdentifier);
        return true;
      });
      return [...sSTSToAdd, ...sSTCollection];
    }
    return sSTCollection;
  }
}
