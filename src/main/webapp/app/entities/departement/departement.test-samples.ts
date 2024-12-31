import { IDepartement, NewDepartement } from './departement.model';

export const sampleWithRequiredData: IDepartement = {
  id: 4097,
  nom: 'bè',
  description: 'découvrir croâ près de',
  email: 'Miriam.Henry87@yahoo.fr',
  telephone: '+33 102987817',
  employeeCount: 4933,
};

export const sampleWithPartialData: IDepartement = {
  id: 21679,
  nom: 'ding',
  description: 'toc-toc chut',
  email: 'Toussaint37@gmail.com',
  telephone: '0391211121',
  employeeCount: 20938,
};

export const sampleWithFullData: IDepartement = {
  id: 19919,
  nom: 'cocorico',
  description: 'avant que trop',
  email: 'Sarah57@gmail.com',
  telephone: '0798911401',
  employeeCount: 27828,
};

export const sampleWithNewData: NewDepartement = {
  nom: 'près',
  description: 'loin lectorat',
  email: 'Quintia.Garnier@hotmail.fr',
  telephone: '+33 362974951',
  employeeCount: 8142,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
