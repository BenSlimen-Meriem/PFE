import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMotif, NewMotif } from '../motif.model';

export type PartialUpdateMotif = Partial<IMotif> & Pick<IMotif, 'id'>;

export type EntityResponseType = HttpResponse<IMotif>;
export type EntityArrayResponseType = HttpResponse<IMotif[]>;

@Injectable({ providedIn: 'root' })
export class MotifService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/motifs');

  create(motif: NewMotif): Observable<EntityResponseType> {
    return this.http.post<IMotif>(this.resourceUrl, motif, { observe: 'response' });
  }

  update(motif: IMotif): Observable<EntityResponseType> {
    return this.http.put<IMotif>(`${this.resourceUrl}/${this.getMotifIdentifier(motif)}`, motif, { observe: 'response' });
  }

  partialUpdate(motif: PartialUpdateMotif): Observable<EntityResponseType> {
    return this.http.patch<IMotif>(`${this.resourceUrl}/${this.getMotifIdentifier(motif)}`, motif, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMotif>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMotif[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMotifIdentifier(motif: Pick<IMotif, 'id'>): number {
    return motif.id;
  }

  compareMotif(o1: Pick<IMotif, 'id'> | null, o2: Pick<IMotif, 'id'> | null): boolean {
    return o1 && o2 ? this.getMotifIdentifier(o1) === this.getMotifIdentifier(o2) : o1 === o2;
  }

  addMotifToCollectionIfMissing<Type extends Pick<IMotif, 'id'>>(
    motifCollection: Type[],
    ...motifsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const motifs: Type[] = motifsToCheck.filter(isPresent);
    if (motifs.length > 0) {
      const motifCollectionIdentifiers = motifCollection.map(motifItem => this.getMotifIdentifier(motifItem));
      const motifsToAdd = motifs.filter(motifItem => {
        const motifIdentifier = this.getMotifIdentifier(motifItem);
        if (motifCollectionIdentifiers.includes(motifIdentifier)) {
          return false;
        }
        motifCollectionIdentifiers.push(motifIdentifier);
        return true;
      });
      return [...motifsToAdd, ...motifCollection];
    }
    return motifCollection;
  }
}
