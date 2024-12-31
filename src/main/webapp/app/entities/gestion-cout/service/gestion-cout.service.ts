import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGestionCout, NewGestionCout } from '../gestion-cout.model';

export type PartialUpdateGestionCout = Partial<IGestionCout> & Pick<IGestionCout, 'id'>;

export type EntityResponseType = HttpResponse<IGestionCout>;
export type EntityArrayResponseType = HttpResponse<IGestionCout[]>;

@Injectable({ providedIn: 'root' })
export class GestionCoutService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/gestion-couts');

  create(gestionCout: NewGestionCout): Observable<EntityResponseType> {
    return this.http.post<IGestionCout>(this.resourceUrl, gestionCout, { observe: 'response' });
  }

  update(gestionCout: IGestionCout): Observable<EntityResponseType> {
    return this.http.put<IGestionCout>(`${this.resourceUrl}/${this.getGestionCoutIdentifier(gestionCout)}`, gestionCout, {
      observe: 'response',
    });
  }

  partialUpdate(gestionCout: PartialUpdateGestionCout): Observable<EntityResponseType> {
    return this.http.patch<IGestionCout>(`${this.resourceUrl}/${this.getGestionCoutIdentifier(gestionCout)}`, gestionCout, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGestionCout>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGestionCout[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGestionCoutIdentifier(gestionCout: Pick<IGestionCout, 'id'>): number {
    return gestionCout.id;
  }

  compareGestionCout(o1: Pick<IGestionCout, 'id'> | null, o2: Pick<IGestionCout, 'id'> | null): boolean {
    return o1 && o2 ? this.getGestionCoutIdentifier(o1) === this.getGestionCoutIdentifier(o2) : o1 === o2;
  }

  addGestionCoutToCollectionIfMissing<Type extends Pick<IGestionCout, 'id'>>(
    gestionCoutCollection: Type[],
    ...gestionCoutsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const gestionCouts: Type[] = gestionCoutsToCheck.filter(isPresent);
    if (gestionCouts.length > 0) {
      const gestionCoutCollectionIdentifiers = gestionCoutCollection.map(gestionCoutItem => this.getGestionCoutIdentifier(gestionCoutItem));
      const gestionCoutsToAdd = gestionCouts.filter(gestionCoutItem => {
        const gestionCoutIdentifier = this.getGestionCoutIdentifier(gestionCoutItem);
        if (gestionCoutCollectionIdentifiers.includes(gestionCoutIdentifier)) {
          return false;
        }
        gestionCoutCollectionIdentifiers.push(gestionCoutIdentifier);
        return true;
      });
      return [...gestionCoutsToAdd, ...gestionCoutCollection];
    }
    return gestionCoutCollection;
  }
}
