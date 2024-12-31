import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicule } from '../vehicule.model';
import { VehiculeService } from '../service/vehicule.service';

const vehiculeResolve = (route: ActivatedRouteSnapshot): Observable<null | IVehicule> => {
  const id = route.params.id;
  if (id) {
    return inject(VehiculeService)
      .find(id)
      .pipe(
        mergeMap((vehicule: HttpResponse<IVehicule>) => {
          if (vehicule.body) {
            return of(vehicule.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default vehiculeResolve;
