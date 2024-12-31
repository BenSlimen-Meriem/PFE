import { IContact, NewContact } from './contact.model';

export const sampleWithRequiredData: IContact = {
  id: 10916,
  nom: 'membre de l’équipe',
  prenom: 'rapide psitt bien que',
  email: 'Auxence28@gmail.com',
  telephone: '0436643913',
};

export const sampleWithPartialData: IContact = {
  id: 17846,
  nom: 'plic extatique',
  prenom: 'essuyer ha ha cuicui',
  email: 'Aurore.Durand@gmail.com',
  telephone: '0601842416',
};

export const sampleWithFullData: IContact = {
  id: 4920,
  nom: 'vorace adversaire ressortir',
  prenom: 'ferme',
  email: 'Innocent_Noel1@hotmail.fr',
  telephone: '+33 114964870',
};

export const sampleWithNewData: NewContact = {
  nom: 'sincère de manière à ce que',
  prenom: 'derrière smack',
  email: 'Aurore.Maillard43@yahoo.fr',
  telephone: '0506273918',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
