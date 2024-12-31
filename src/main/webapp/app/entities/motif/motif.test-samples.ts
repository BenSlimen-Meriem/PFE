import { IMotif, NewMotif } from './motif.model';

export const sampleWithRequiredData: IMotif = {
  id: 26302,
  code: 'tellement habile à moins de',
  libelle: 'toc à force de circulaire',
};

export const sampleWithPartialData: IMotif = {
  id: 27841,
  code: 'responsable',
  libelle: 'mieux',
  description: 'coac coac',
};

export const sampleWithFullData: IMotif = {
  id: 24279,
  code: 'adversaire intrépide là',
  libelle: 'équipe de recherche',
  description: 'personnel professionnel adorable',
  priorite: 10416,
};

export const sampleWithNewData: NewMotif = {
  code: 'habile faire',
  libelle: 'repartir ressusciter smack',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
