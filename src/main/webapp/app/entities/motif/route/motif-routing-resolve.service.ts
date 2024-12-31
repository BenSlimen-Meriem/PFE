import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMotif } from '../motif.model';
import { MotifService } from '../service/motif.service';

const motifResolve = (route: ActivatedRouteSnapshot): Observable<null | IMotif> => {
  const id = route.params.id;
  if (id) {
    return inject(MotifService)
      .find(id)
      .pipe(
        mergeMap((motif: HttpResponse<IMotif>) => {
          if (motif.body) {
            return of(motif.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default motifResolve;
