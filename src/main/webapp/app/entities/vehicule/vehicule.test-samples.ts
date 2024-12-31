import { IVehicule, NewVehicule } from './vehicule.model';

export const sampleWithRequiredData: IVehicule = {
  id: 24059,
  model: 'pis clientèle',
  numeroCarteGrise: 'du fait que sous couleur de afin que',
  numSerie: 'dresser de peur que électorat',
  statut: 'DISPONIBLE',
  type: 'derechef adepte',
};

export const sampleWithPartialData: IVehicule = {
  id: 32100,
  model: 'de crainte que clientèle',
  numeroCarteGrise: 'concurrence',
  numSerie: 'souple maigre timide',
  statut: 'DISPONIBLE',
  type: "en guise de hormis à l'instar de",
};

export const sampleWithFullData: IVehicule = {
  id: 1566,
  model: 'manifester bof patientèle',
  numeroCarteGrise: 'terriblement conseil d’administration ci',
  numSerie: 'à peine puisque suivre',
  statut: 'EN_MISSION',
  type: 'pour que',
};

export const sampleWithNewData: NewVehicule = {
  model: 'gestionnaire',
  numeroCarteGrise: 'enfiler aimable',
  numSerie: 'cot cot minuscule',
  statut: 'EN_MISSION',
  type: 'sombre',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
