export interface IMotif {
  id: number;
  code?: string | null;
  libelle?: string | null;
  description?: string | null;
  priorite?: number | null;
}

export type NewMotif = Omit<IMotif, 'id'> & { id: null };
