import { IPlanificateur, NewPlanificateur } from './planificateur.model';

export const sampleWithRequiredData: IPlanificateur = {
  id: 19121,
  nom: 'entièrement main-d’œuvre hors',
  niveauExpertise: 'badaboum tchou tchouu soudain',
  disponibilite: false,
};

export const sampleWithPartialData: IPlanificateur = {
  id: 24944,
  nom: 'concurrence',
  niveauExpertise: 'sitôt que guide concevoir',
  disponibilite: true,
};

export const sampleWithFullData: IPlanificateur = {
  id: 123,
  nom: 'loin de',
  niveauExpertise: 'sans que aux alentours de',
  disponibilite: true,
};

export const sampleWithNewData: NewPlanificateur = {
  nom: 'vivace secours dedans',
  niveauExpertise: 'tchou tchouu population du Québec',
  disponibilite: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
