import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 16289,
  raisonSociale: 'du côté de chialer oh',
  identifiantUnique: 'à même ailleurs partenaire',
};

export const sampleWithPartialData: IClient = {
  id: 13353,
  raisonSociale: "coucher à l'insu de",
  identifiantUnique: 'même mélanger',
};

export const sampleWithFullData: IClient = {
  id: 31496,
  raisonSociale: 'bzzz lectorat séculaire',
  identifiantUnique: 'tellement de peur que',
};

export const sampleWithNewData: NewClient = {
  raisonSociale: 'tôt à la merci',
  identifiantUnique: 'infime tranquille',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
