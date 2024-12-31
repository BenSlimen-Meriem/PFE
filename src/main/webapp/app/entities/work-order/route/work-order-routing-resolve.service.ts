import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWorkOrder } from '../work-order.model';
import { WorkOrderService } from '../service/work-order.service';

const workOrderResolve = (route: ActivatedRouteSnapshot): Observable<null | IWorkOrder> => {
  const id = route.params.id;
  if (id) {
    return inject(WorkOrderService)
      .find(id)
      .pipe(
        mergeMap((workOrder: HttpResponse<IWorkOrder>) => {
          if (workOrder.body) {
            return of(workOrder.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default workOrderResolve;
