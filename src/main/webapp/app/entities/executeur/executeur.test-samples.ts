import { IExecuteur, NewExecuteur } from './executeur.model';

export const sampleWithRequiredData: IExecuteur = {
  id: 1900,
  nom: 'de peur que recta verser',
  niveauExpertise: 'par rapport à étant donné que partenaire',
  disponibilite: false,
};

export const sampleWithPartialData: IExecuteur = {
  id: 16301,
  nom: 'de manière à ce que de la part de spécialiste',
  niveauExpertise: 'autrement guide',
  disponibilite: true,
};

export const sampleWithFullData: IExecuteur = {
  id: 30213,
  nom: 'détendre',
  niveauExpertise: 'en dépit de tenir',
  disponibilite: false,
};

export const sampleWithNewData: NewExecuteur = {
  nom: 'par suite de',
  niveauExpertise: 'pisser résister chut',
  disponibilite: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
