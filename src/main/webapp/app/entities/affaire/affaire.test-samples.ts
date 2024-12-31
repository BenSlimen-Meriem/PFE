import { IAffaire, NewAffaire } from './affaire.model';

export const sampleWithRequiredData: IAffaire = {
  id: 26339,
  reference: 'influencer',
};

export const sampleWithPartialData: IAffaire = {
  id: 9609,
  reference: "après à l'égard de aux alentours de",
  description: 'volontiers',
};

export const sampleWithFullData: IAffaire = {
  id: 9439,
  reference: 'pour que en faveur de',
  description: 'sitôt que sitôt',
};

export const sampleWithNewData: NewAffaire = {
  reference: 'frayer',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
