import { IWorkOrder } from 'app/entities/work-order/work-order.model';
import { StatutVehicule } from 'app/entities/enumerations/statut-vehicule.model';

export interface IVehicule {
  id: number;
  model?: string | null;
  numeroCarteGrise?: string | null;
  numSerie?: string | null;
  statut?: keyof typeof StatutVehicule | null;
  type?: string | null;
  workOrders?: IWorkOrder[] | null;
}

export type NewVehicule = Omit<IVehicule, 'id'> & { id: null };
