import dayjs from 'dayjs/esm';
import { IWorkOrder } from 'app/entities/work-order/work-order.model';

export interface IOrdreFacturation {
  id: number;
  date?: dayjs.Dayjs | null;
  bonDeCommande?: string | null;
  facture?: string | null;
  workOrder?: IWorkOrder | null;
}

export type NewOrdreFacturation = Omit<IOrdreFacturation, 'id'> & { id: null };
