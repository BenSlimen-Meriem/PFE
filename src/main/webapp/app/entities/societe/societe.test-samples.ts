import { ISociete, NewSociete } from './societe.model';

export const sampleWithRequiredData: ISociete = {
  id: 32433,
  raisonSociale: 'téméraire hystérique vu que',
  identifiantUnique: 'énorme verser',
};

export const sampleWithPartialData: ISociete = {
  id: 24313,
  raisonSociale: 'drelin',
  identifiantUnique: 'mieux',
  pays: 'déboucher hors de',
  telephone: '0456453479',
  email: 'Girart25@hotmail.fr',
};

export const sampleWithFullData: ISociete = {
  id: 14789,
  raisonSociale: 'toc financer gai',
  identifiantUnique: 'loin de',
  registreCommerce: 'tard accomplir',
  codeArticle: 'arrêter vlan',
  adresse: 'commis parlementaire négliger',
  codePostal: 'contre dès que',
  pays: 'parlementaire',
  telephone: '+33 506819680',
  fax: 'sitôt que déchiffrer police',
  email: 'Arnaude_Francois@yahoo.fr',
  agence: 'lorsque aux environs de',
};

export const sampleWithNewData: NewSociete = {
  raisonSociale: 'pff ha chialer',
  identifiantUnique: 'devant',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
