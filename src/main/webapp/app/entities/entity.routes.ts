import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'pfeApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'societe',
    data: { pageTitle: 'pfeApp.societe.home.title' },
    loadChildren: () => import('./societe/societe.routes'),
  },
  {
    path: 'departement',
    data: { pageTitle: 'pfeApp.departement.home.title' },
    loadChildren: () => import('./departement/departement.routes'),
  },
  {
    path: 'client',
    data: { pageTitle: 'pfeApp.client.home.title' },
    loadChildren: () => import('./client/client.routes'),
  },
  {
    path: 'contact',
    data: { pageTitle: 'pfeApp.contact.home.title' },
    loadChildren: () => import('./contact/contact.routes'),
  },
  {
    path: 'site',
    data: { pageTitle: 'pfeApp.site.home.title' },
    loadChildren: () => import('./site/site.routes'),
  },
  {
    path: 'affaire',
    data: { pageTitle: 'pfeApp.affaire.home.title' },
    loadChildren: () => import('./affaire/affaire.routes'),
  },
  {
    path: 'work-order',
    data: { pageTitle: 'pfeApp.workOrder.home.title' },
    loadChildren: () => import('./work-order/work-order.routes'),
  },
  {
    path: 'employee',
    data: { pageTitle: 'pfeApp.employee.home.title' },
    loadChildren: () => import('./employee/employee.routes'),
  },
  {
    path: 'executeur',
    data: { pageTitle: 'pfeApp.executeur.home.title' },
    loadChildren: () => import('./executeur/executeur.routes'),
  },
  {
    path: 'planificateur',
    data: { pageTitle: 'pfeApp.planificateur.home.title' },
    loadChildren: () => import('./planificateur/planificateur.routes'),
  },
  {
    path: 'gestion-cout',
    data: { pageTitle: 'pfeApp.gestionCout.home.title' },
    loadChildren: () => import('./gestion-cout/gestion-cout.routes'),
  },
  {
    path: 'ordre-travail-client',
    data: { pageTitle: 'pfeApp.ordreTravailClient.home.title' },
    loadChildren: () => import('./ordre-travail-client/ordre-travail-client.routes'),
  },
  {
    path: 'vehicule',
    data: { pageTitle: 'pfeApp.vehicule.home.title' },
    loadChildren: () => import('./vehicule/vehicule.routes'),
  },
  {
    path: 'article',
    data: { pageTitle: 'pfeApp.article.home.title' },
    loadChildren: () => import('./article/article.routes'),
  },
  {
    path: 'motif',
    data: { pageTitle: 'pfeApp.motif.home.title' },
    loadChildren: () => import('./motif/motif.routes'),
  },
  {
    path: 'ordre-facturation',
    data: { pageTitle: 'pfeApp.ordreFacturation.home.title' },
    loadChildren: () => import('./ordre-facturation/ordre-facturation.routes'),
  },
  {
    path: 'sst',
    data: { pageTitle: 'pfeApp.sST.home.title' },
    loadChildren: () => import('./sst/sst.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
