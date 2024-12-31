import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISST } from '../sst.model';
import { SSTService } from '../service/sst.service';

const sSTResolve = (route: ActivatedRouteSnapshot): Observable<null | ISST> => {
  const id = route.params.id;
  if (id) {
    return inject(SSTService)
      .find(id)
      .pipe(
        mergeMap((sST: HttpResponse<ISST>) => {
          if (sST.body) {
            return of(sST.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default sSTResolve;
