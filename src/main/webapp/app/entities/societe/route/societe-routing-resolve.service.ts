import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISociete } from '../societe.model';
import { SocieteService } from '../service/societe.service';

const societeResolve = (route: ActivatedRouteSnapshot): Observable<null | ISociete> => {
  const id = route.params.id;
  if (id) {
    return inject(SocieteService)
      .find(id)
      .pipe(
        mergeMap((societe: HttpResponse<ISociete>) => {
          if (societe.body) {
            return of(societe.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default societeResolve;
