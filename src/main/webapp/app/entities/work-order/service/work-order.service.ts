import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWorkOrder, NewWorkOrder } from '../work-order.model';

export type PartialUpdateWorkOrder = Partial<IWorkOrder> & Pick<IWorkOrder, 'id'>;

type RestOf<T extends IWorkOrder | NewWorkOrder> = Omit<T, 'dateHeureDebutPrevisionnelle' | 'dateHeureFinPrevisionnelle'> & {
  dateHeureDebutPrevisionnelle?: string | null;
  dateHeureFinPrevisionnelle?: string | null;
};

export type RestWorkOrder = RestOf<IWorkOrder>;

export type NewRestWorkOrder = RestOf<NewWorkOrder>;

export type PartialUpdateRestWorkOrder = RestOf<PartialUpdateWorkOrder>;

export type EntityResponseType = HttpResponse<IWorkOrder>;
export type EntityArrayResponseType = HttpResponse<IWorkOrder[]>;

@Injectable({ providedIn: 'root' })
export class WorkOrderService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/work-orders');

  create(workOrder: NewWorkOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workOrder);
    return this.http
      .post<RestWorkOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(workOrder: IWorkOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workOrder);
    return this.http
      .put<RestWorkOrder>(`${this.resourceUrl}/${this.getWorkOrderIdentifier(workOrder)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(workOrder: PartialUpdateWorkOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workOrder);
    return this.http
      .patch<RestWorkOrder>(`${this.resourceUrl}/${this.getWorkOrderIdentifier(workOrder)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestWorkOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestWorkOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getWorkOrderIdentifier(workOrder: Pick<IWorkOrder, 'id'>): number {
    return workOrder.id;
  }

  compareWorkOrder(o1: Pick<IWorkOrder, 'id'> | null, o2: Pick<IWorkOrder, 'id'> | null): boolean {
    return o1 && o2 ? this.getWorkOrderIdentifier(o1) === this.getWorkOrderIdentifier(o2) : o1 === o2;
  }

  addWorkOrderToCollectionIfMissing<Type extends Pick<IWorkOrder, 'id'>>(
    workOrderCollection: Type[],
    ...workOrdersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const workOrders: Type[] = workOrdersToCheck.filter(isPresent);
    if (workOrders.length > 0) {
      const workOrderCollectionIdentifiers = workOrderCollection.map(workOrderItem => this.getWorkOrderIdentifier(workOrderItem));
      const workOrdersToAdd = workOrders.filter(workOrderItem => {
        const workOrderIdentifier = this.getWorkOrderIdentifier(workOrderItem);
        if (workOrderCollectionIdentifiers.includes(workOrderIdentifier)) {
          return false;
        }
        workOrderCollectionIdentifiers.push(workOrderIdentifier);
        return true;
      });
      return [...workOrdersToAdd, ...workOrderCollection];
    }
    return workOrderCollection;
  }

  protected convertDateFromClient<T extends IWorkOrder | NewWorkOrder | PartialUpdateWorkOrder>(workOrder: T): RestOf<T> {
    return {
      ...workOrder,
      dateHeureDebutPrevisionnelle: workOrder.dateHeureDebutPrevisionnelle?.toJSON() ?? null,
      dateHeureFinPrevisionnelle: workOrder.dateHeureFinPrevisionnelle?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restWorkOrder: RestWorkOrder): IWorkOrder {
    return {
      ...restWorkOrder,
      dateHeureDebutPrevisionnelle: restWorkOrder.dateHeureDebutPrevisionnelle
        ? dayjs(restWorkOrder.dateHeureDebutPrevisionnelle)
        : undefined,
      dateHeureFinPrevisionnelle: restWorkOrder.dateHeureFinPrevisionnelle ? dayjs(restWorkOrder.dateHeureFinPrevisionnelle) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestWorkOrder>): HttpResponse<IWorkOrder> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestWorkOrder[]>): HttpResponse<IWorkOrder[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
