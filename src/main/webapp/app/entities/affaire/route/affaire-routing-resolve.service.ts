import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAffaire } from '../affaire.model';
import { AffaireService } from '../service/affaire.service';

const affaireResolve = (route: ActivatedRouteSnapshot): Observable<null | IAffaire> => {
  const id = route.params.id;
  if (id) {
    return inject(AffaireService)
      .find(id)
      .pipe(
        mergeMap((affaire: HttpResponse<IAffaire>) => {
          if (affaire.body) {
            return of(affaire.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default affaireResolve;
