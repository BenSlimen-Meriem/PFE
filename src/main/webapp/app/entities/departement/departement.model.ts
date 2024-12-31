import { IEmployee } from 'app/entities/employee/employee.model';
import { ISociete } from 'app/entities/societe/societe.model';

export interface IDepartement {
  id: number;
  nom?: string | null;
  description?: string | null;
  email?: string | null;
  telephone?: string | null;
  employeeCount?: number | null;
  manager?: IEmployee | null;
  societe?: ISociete | null;
}

export type NewDepartement = Omit<IDepartement, 'id'> & { id: null };
