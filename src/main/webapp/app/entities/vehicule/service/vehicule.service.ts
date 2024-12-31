import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicule, NewVehicule } from '../vehicule.model';

export type PartialUpdateVehicule = Partial<IVehicule> & Pick<IVehicule, 'id'>;

export type EntityResponseType = HttpResponse<IVehicule>;
export type EntityArrayResponseType = HttpResponse<IVehicule[]>;

@Injectable({ providedIn: 'root' })
export class VehiculeService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicules');

  create(vehicule: NewVehicule): Observable<EntityResponseType> {
    return this.http.post<IVehicule>(this.resourceUrl, vehicule, { observe: 'response' });
  }

  update(vehicule: IVehicule): Observable<EntityResponseType> {
    return this.http.put<IVehicule>(`${this.resourceUrl}/${this.getVehiculeIdentifier(vehicule)}`, vehicule, { observe: 'response' });
  }

  partialUpdate(vehicule: PartialUpdateVehicule): Observable<EntityResponseType> {
    return this.http.patch<IVehicule>(`${this.resourceUrl}/${this.getVehiculeIdentifier(vehicule)}`, vehicule, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVehicule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVehicule[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVehiculeIdentifier(vehicule: Pick<IVehicule, 'id'>): number {
    return vehicule.id;
  }

  compareVehicule(o1: Pick<IVehicule, 'id'> | null, o2: Pick<IVehicule, 'id'> | null): boolean {
    return o1 && o2 ? this.getVehiculeIdentifier(o1) === this.getVehiculeIdentifier(o2) : o1 === o2;
  }

  addVehiculeToCollectionIfMissing<Type extends Pick<IVehicule, 'id'>>(
    vehiculeCollection: Type[],
    ...vehiculesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const vehicules: Type[] = vehiculesToCheck.filter(isPresent);
    if (vehicules.length > 0) {
      const vehiculeCollectionIdentifiers = vehiculeCollection.map(vehiculeItem => this.getVehiculeIdentifier(vehiculeItem));
      const vehiculesToAdd = vehicules.filter(vehiculeItem => {
        const vehiculeIdentifier = this.getVehiculeIdentifier(vehiculeItem);
        if (vehiculeCollectionIdentifiers.includes(vehiculeIdentifier)) {
          return false;
        }
        vehiculeCollectionIdentifiers.push(vehiculeIdentifier);
        return true;
      });
      return [...vehiculesToAdd, ...vehiculeCollection];
    }
    return vehiculeCollection;
  }
}
