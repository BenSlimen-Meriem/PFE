import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import GestionCoutResolve from './route/gestion-cout-routing-resolve.service';

const gestionCoutRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/gestion-cout.component').then(m => m.GestionCoutComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/gestion-cout-detail.component').then(m => m.GestionCoutDetailComponent),
    resolve: {
      gestionCout: GestionCoutResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/gestion-cout-update.component').then(m => m.GestionCoutUpdateComponent),
    resolve: {
      gestionCout: GestionCoutResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/gestion-cout-update.component').then(m => m.GestionCoutUpdateComponent),
    resolve: {
      gestionCout: GestionCoutResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default gestionCoutRoute;
