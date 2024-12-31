import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: 8899,
  nom: 'antique garantir',
};

export const sampleWithPartialData: IEmployee = {
  id: 5984,
  nom: 'délégation',
  qualification: 'membre titulaire prout pleuvoir',
};

export const sampleWithFullData: IEmployee = {
  id: 19019,
  nom: 'alentour lors au-dessus',
  qualification: 'main-d’œuvre maigre',
};

export const sampleWithNewData: NewEmployee = {
  nom: 'trop',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
