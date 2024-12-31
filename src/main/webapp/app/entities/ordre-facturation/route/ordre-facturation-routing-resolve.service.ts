import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrdreFacturation } from '../ordre-facturation.model';
import { OrdreFacturationService } from '../service/ordre-facturation.service';

const ordreFacturationResolve = (route: ActivatedRouteSnapshot): Observable<null | IOrdreFacturation> => {
  const id = route.params.id;
  if (id) {
    return inject(OrdreFacturationService)
      .find(id)
      .pipe(
        mergeMap((ordreFacturation: HttpResponse<IOrdreFacturation>) => {
          if (ordreFacturation.body) {
            return of(ordreFacturation.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default ordreFacturationResolve;
