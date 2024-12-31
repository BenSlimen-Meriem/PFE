import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import OrdreTravailClientResolve from './route/ordre-travail-client-routing-resolve.service';

const ordreTravailClientRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/ordre-travail-client.component').then(m => m.OrdreTravailClientComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/ordre-travail-client-detail.component').then(m => m.OrdreTravailClientDetailComponent),
    resolve: {
      ordreTravailClient: OrdreTravailClientResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/ordre-travail-client-update.component').then(m => m.OrdreTravailClientUpdateComponent),
    resolve: {
      ordreTravailClient: OrdreTravailClientResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/ordre-travail-client-update.component').then(m => m.OrdreTravailClientUpdateComponent),
    resolve: {
      ordreTravailClient: OrdreTravailClientResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default ordreTravailClientRoute;
