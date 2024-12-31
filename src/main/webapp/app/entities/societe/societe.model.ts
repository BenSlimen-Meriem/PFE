export interface ISociete {
  id: number;
  raisonSociale?: string | null;
  identifiantUnique?: string | null;
  registreCommerce?: string | null;
  codeArticle?: string | null;
  adresse?: string | null;
  codePostal?: string | null;
  pays?: string | null;
  telephone?: string | null;
  fax?: string | null;
  email?: string | null;
  agence?: string | null;
}

export type NewSociete = Omit<ISociete, 'id'> & { id: null };
