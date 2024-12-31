import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExecuteur } from '../executeur.model';
import { ExecuteurService } from '../service/executeur.service';

const executeurResolve = (route: ActivatedRouteSnapshot): Observable<null | IExecuteur> => {
  const id = route.params.id;
  if (id) {
    return inject(ExecuteurService)
      .find(id)
      .pipe(
        mergeMap((executeur: HttpResponse<IExecuteur>) => {
          if (executeur.body) {
            return of(executeur.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default executeurResolve;
