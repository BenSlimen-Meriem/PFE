import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import WorkOrderResolve from './route/work-order-routing-resolve.service';

const workOrderRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/work-order.component').then(m => m.WorkOrderComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/work-order-detail.component').then(m => m.WorkOrderDetailComponent),
    resolve: {
      workOrder: WorkOrderResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/work-order-update.component').then(m => m.WorkOrderUpdateComponent),
    resolve: {
      workOrder: WorkOrderResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/work-order-update.component').then(m => m.WorkOrderUpdateComponent),
    resolve: {
      workOrder: WorkOrderResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default workOrderRoute;
