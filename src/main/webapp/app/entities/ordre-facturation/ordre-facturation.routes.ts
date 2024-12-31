import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import OrdreFacturationResolve from './route/ordre-facturation-routing-resolve.service';

const ordreFacturationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/ordre-facturation.component').then(m => m.OrdreFacturationComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/ordre-facturation-detail.component').then(m => m.OrdreFacturationDetailComponent),
    resolve: {
      ordreFacturation: OrdreFacturationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/ordre-facturation-update.component').then(m => m.OrdreFacturationUpdateComponent),
    resolve: {
      ordreFacturation: OrdreFacturationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/ordre-facturation-update.component').then(m => m.OrdreFacturationUpdateComponent),
    resolve: {
      ordreFacturation: OrdreFacturationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default ordreFacturationRoute;
