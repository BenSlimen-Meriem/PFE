import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import MotifResolve from './route/motif-routing-resolve.service';

const motifRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/motif.component').then(m => m.MotifComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/motif-detail.component').then(m => m.MotifDetailComponent),
    resolve: {
      motif: MotifResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/motif-update.component').then(m => m.MotifUpdateComponent),
    resolve: {
      motif: MotifResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/motif-update.component').then(m => m.MotifUpdateComponent),
    resolve: {
      motif: MotifResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default motifRoute;
