import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AffaireResolve from './route/affaire-routing-resolve.service';

const affaireRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/affaire.component').then(m => m.AffaireComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/affaire-detail.component').then(m => m.AffaireDetailComponent),
    resolve: {
      affaire: AffaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/affaire-update.component').then(m => m.AffaireUpdateComponent),
    resolve: {
      affaire: AffaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/affaire-update.component').then(m => m.AffaireUpdateComponent),
    resolve: {
      affaire: AffaireResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default affaireRoute;
