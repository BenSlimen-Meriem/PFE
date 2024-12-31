import dayjs from 'dayjs/esm';
import { IAffaire } from 'app/entities/affaire/affaire.model';
import { IMotif } from 'app/entities/motif/motif.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { IArticle } from 'app/entities/article/article.model';
import { IVehicule } from 'app/entities/vehicule/vehicule.model';
import { ISite } from 'app/entities/site/site.model';
import { StatutMission } from 'app/entities/enumerations/statut-mission.model';

export interface IWorkOrder {
  id: number;
  demandeur?: string | null;
  nature?: string | null;
  motifDescription?: string | null;
  dateHeureDebutPrevisionnelle?: dayjs.Dayjs | null;
  dateHeureFinPrevisionnelle?: dayjs.Dayjs | null;
  vehicule?: string | null;
  materielUtilise?: string | null;
  article?: string | null;
  membreMission?: string | null;
  responsableMission?: string | null;
  statut?: keyof typeof StatutMission | null;
  affaire?: IAffaire | null;
  motif?: IMotif | null;
  employees?: IEmployee[] | null;
  articles?: IArticle[] | null;
  vehicules?: IVehicule[] | null;
  site?: ISite | null;
}

export type NewWorkOrder = Omit<IWorkOrder, 'id'> & { id: null };
