import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlanificateur } from '../planificateur.model';
import { PlanificateurService } from '../service/planificateur.service';

const planificateurResolve = (route: ActivatedRouteSnapshot): Observable<null | IPlanificateur> => {
  const id = route.params.id;
  if (id) {
    return inject(PlanificateurService)
      .find(id)
      .pipe(
        mergeMap((planificateur: HttpResponse<IPlanificateur>) => {
          if (planificateur.body) {
            return of(planificateur.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default planificateurResolve;
