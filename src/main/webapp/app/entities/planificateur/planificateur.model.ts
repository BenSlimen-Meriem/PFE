import { IEmployee } from 'app/entities/employee/employee.model';

export interface IPlanificateur {
  id: number;
  nom?: string | null;
  niveauExpertise?: string | null;
  disponibilite?: boolean | null;
  employee?: IEmployee | null;
}

export type NewPlanificateur = Omit<IPlanificateur, 'id'> & { id: null };
