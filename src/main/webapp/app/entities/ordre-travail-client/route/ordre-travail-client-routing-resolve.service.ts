import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrdreTravailClient } from '../ordre-travail-client.model';
import { OrdreTravailClientService } from '../service/ordre-travail-client.service';

const ordreTravailClientResolve = (route: ActivatedRouteSnapshot): Observable<null | IOrdreTravailClient> => {
  const id = route.params.id;
  if (id) {
    return inject(OrdreTravailClientService)
      .find(id)
      .pipe(
        mergeMap((ordreTravailClient: HttpResponse<IOrdreTravailClient>) => {
          if (ordreTravailClient.body) {
            return of(ordreTravailClient.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default ordreTravailClientResolve;
