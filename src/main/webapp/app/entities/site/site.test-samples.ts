import { ISite, NewSite } from './site.model';

export const sampleWithRequiredData: ISite = {
  id: 15724,
  nom: 'présidence tandis que entièrement',
};

export const sampleWithPartialData: ISite = {
  id: 3910,
  nom: 'extrêmement grâce à',
};

export const sampleWithFullData: ISite = {
  id: 31836,
  nom: 'cyan',
  adresse: 'commissionnaire trop',
};

export const sampleWithNewData: NewSite = {
  nom: 'délégation',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
