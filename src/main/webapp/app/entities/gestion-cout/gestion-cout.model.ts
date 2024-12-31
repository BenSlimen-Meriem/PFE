import { IPlanificateur } from 'app/entities/planificateur/planificateur.model';

export interface IGestionCout {
  id: number;
  time?: number | null;
  cout?: number | null;
  planificateur?: IPlanificateur | null;
}

export type NewGestionCout = Omit<IGestionCout, 'id'> & { id: null };
