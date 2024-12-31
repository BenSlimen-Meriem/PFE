import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PlanificateurResolve from './route/planificateur-routing-resolve.service';

const planificateurRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/planificateur.component').then(m => m.PlanificateurComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/planificateur-detail.component').then(m => m.PlanificateurDetailComponent),
    resolve: {
      planificateur: PlanificateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/planificateur-update.component').then(m => m.PlanificateurUpdateComponent),
    resolve: {
      planificateur: PlanificateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/planificateur-update.component').then(m => m.PlanificateurUpdateComponent),
    resolve: {
      planificateur: PlanificateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default planificateurRoute;
