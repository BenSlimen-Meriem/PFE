import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ExecuteurResolve from './route/executeur-routing-resolve.service';

const executeurRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/executeur.component').then(m => m.ExecuteurComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/executeur-detail.component').then(m => m.ExecuteurDetailComponent),
    resolve: {
      executeur: ExecuteurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/executeur-update.component').then(m => m.ExecuteurUpdateComponent),
    resolve: {
      executeur: ExecuteurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/executeur-update.component').then(m => m.ExecuteurUpdateComponent),
    resolve: {
      executeur: ExecuteurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default executeurRoute;
