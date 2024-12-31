import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import VehiculeResolve from './route/vehicule-routing-resolve.service';

const vehiculeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/vehicule.component').then(m => m.VehiculeComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/vehicule-detail.component').then(m => m.VehiculeDetailComponent),
    resolve: {
      vehicule: VehiculeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/vehicule-update.component').then(m => m.VehiculeUpdateComponent),
    resolve: {
      vehicule: VehiculeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/vehicule-update.component').then(m => m.VehiculeUpdateComponent),
    resolve: {
      vehicule: VehiculeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default vehiculeRoute;
