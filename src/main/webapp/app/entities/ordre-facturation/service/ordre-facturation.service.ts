import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrdreFacturation, NewOrdreFacturation } from '../ordre-facturation.model';

export type PartialUpdateOrdreFacturation = Partial<IOrdreFacturation> & Pick<IOrdreFacturation, 'id'>;

type RestOf<T extends IOrdreFacturation | NewOrdreFacturation> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestOrdreFacturation = RestOf<IOrdreFacturation>;

export type NewRestOrdreFacturation = RestOf<NewOrdreFacturation>;

export type PartialUpdateRestOrdreFacturation = RestOf<PartialUpdateOrdreFacturation>;

export type EntityResponseType = HttpResponse<IOrdreFacturation>;
export type EntityArrayResponseType = HttpResponse<IOrdreFacturation[]>;

@Injectable({ providedIn: 'root' })
export class OrdreFacturationService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ordre-facturations');

  create(ordreFacturation: NewOrdreFacturation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ordreFacturation);
    return this.http
      .post<RestOrdreFacturation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(ordreFacturation: IOrdreFacturation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ordreFacturation);
    return this.http
      .put<RestOrdreFacturation>(`${this.resourceUrl}/${this.getOrdreFacturationIdentifier(ordreFacturation)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(ordreFacturation: PartialUpdateOrdreFacturation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ordreFacturation);
    return this.http
      .patch<RestOrdreFacturation>(`${this.resourceUrl}/${this.getOrdreFacturationIdentifier(ordreFacturation)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestOrdreFacturation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOrdreFacturation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrdreFacturationIdentifier(ordreFacturation: Pick<IOrdreFacturation, 'id'>): number {
    return ordreFacturation.id;
  }

  compareOrdreFacturation(o1: Pick<IOrdreFacturation, 'id'> | null, o2: Pick<IOrdreFacturation, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrdreFacturationIdentifier(o1) === this.getOrdreFacturationIdentifier(o2) : o1 === o2;
  }

  addOrdreFacturationToCollectionIfMissing<Type extends Pick<IOrdreFacturation, 'id'>>(
    ordreFacturationCollection: Type[],
    ...ordreFacturationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ordreFacturations: Type[] = ordreFacturationsToCheck.filter(isPresent);
    if (ordreFacturations.length > 0) {
      const ordreFacturationCollectionIdentifiers = ordreFacturationCollection.map(ordreFacturationItem =>
        this.getOrdreFacturationIdentifier(ordreFacturationItem),
      );
      const ordreFacturationsToAdd = ordreFacturations.filter(ordreFacturationItem => {
        const ordreFacturationIdentifier = this.getOrdreFacturationIdentifier(ordreFacturationItem);
        if (ordreFacturationCollectionIdentifiers.includes(ordreFacturationIdentifier)) {
          return false;
        }
        ordreFacturationCollectionIdentifiers.push(ordreFacturationIdentifier);
        return true;
      });
      return [...ordreFacturationsToAdd, ...ordreFacturationCollection];
    }
    return ordreFacturationCollection;
  }

  protected convertDateFromClient<T extends IOrdreFacturation | NewOrdreFacturation | PartialUpdateOrdreFacturation>(
    ordreFacturation: T,
  ): RestOf<T> {
    return {
      ...ordreFacturation,
      date: ordreFacturation.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restOrdreFacturation: RestOrdreFacturation): IOrdreFacturation {
    return {
      ...restOrdreFacturation,
      date: restOrdreFacturation.date ? dayjs(restOrdreFacturation.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOrdreFacturation>): HttpResponse<IOrdreFacturation> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOrdreFacturation[]>): HttpResponse<IOrdreFacturation[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
