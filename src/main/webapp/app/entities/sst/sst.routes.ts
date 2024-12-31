import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SSTResolve from './route/sst-routing-resolve.service';

const sSTRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sst.component').then(m => m.SSTComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/sst-detail.component').then(m => m.SSTDetailComponent),
    resolve: {
      sST: SSTResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/sst-update.component').then(m => m.SSTUpdateComponent),
    resolve: {
      sST: SSTResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/sst-update.component').then(m => m.SSTUpdateComponent),
    resolve: {
      sST: SSTResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sSTRoute;
