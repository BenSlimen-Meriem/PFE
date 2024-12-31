import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGestionCout } from '../gestion-cout.model';
import { GestionCoutService } from '../service/gestion-cout.service';

const gestionCoutResolve = (route: ActivatedRouteSnapshot): Observable<null | IGestionCout> => {
  const id = route.params.id;
  if (id) {
    return inject(GestionCoutService)
      .find(id)
      .pipe(
        mergeMap((gestionCout: HttpResponse<IGestionCout>) => {
          if (gestionCout.body) {
            return of(gestionCout.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default gestionCoutResolve;
