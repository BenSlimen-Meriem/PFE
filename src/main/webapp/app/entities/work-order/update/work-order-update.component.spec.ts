import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IAffaire } from 'app/entities/affaire/affaire.model';
import { AffaireService } from 'app/entities/affaire/service/affaire.service';
import { IMotif } from 'app/entities/motif/motif.model';
import { MotifService } from 'app/entities/motif/service/motif.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IArticle } from 'app/entities/article/article.model';
import { ArticleService } from 'app/entities/article/service/article.service';
import { IVehicule } from 'app/entities/vehicule/vehicule.model';
import { VehiculeService } from 'app/entities/vehicule/service/vehicule.service';
import { ISite } from 'app/entities/site/site.model';
import { SiteService } from 'app/entities/site/service/site.service';
import { IWorkOrder } from '../work-order.model';
import { WorkOrderService } from '../service/work-order.service';
import { WorkOrderFormService } from './work-order-form.service';

import { WorkOrderUpdateComponent } from './work-order-update.component';

describe('WorkOrder Management Update Component', () => {
  let comp: WorkOrderUpdateComponent;
  let fixture: ComponentFixture<WorkOrderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let workOrderFormService: WorkOrderFormService;
  let workOrderService: WorkOrderService;
  let affaireService: AffaireService;
  let motifService: MotifService;
  let employeeService: EmployeeService;
  let articleService: ArticleService;
  let vehiculeService: VehiculeService;
  let siteService: SiteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [WorkOrderUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(WorkOrderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WorkOrderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    workOrderFormService = TestBed.inject(WorkOrderFormService);
    workOrderService = TestBed.inject(WorkOrderService);
    affaireService = TestBed.inject(AffaireService);
    motifService = TestBed.inject(MotifService);
    employeeService = TestBed.inject(EmployeeService);
    articleService = TestBed.inject(ArticleService);
    vehiculeService = TestBed.inject(VehiculeService);
    siteService = TestBed.inject(SiteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Affaire query and add missing value', () => {
      const workOrder: IWorkOrder = { id: 6339 };
      const affaire: IAffaire = { id: 3095 };
      workOrder.affaire = affaire;

      const affaireCollection: IAffaire[] = [{ id: 3095 }];
      jest.spyOn(affaireService, 'query').mockReturnValue(of(new HttpResponse({ body: affaireCollection })));
      const additionalAffaires = [affaire];
      const expectedCollection: IAffaire[] = [...additionalAffaires, ...affaireCollection];
      jest.spyOn(affaireService, 'addAffaireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ workOrder });
      comp.ngOnInit();

      expect(affaireService.query).toHaveBeenCalled();
      expect(affaireService.addAffaireToCollectionIfMissing).toHaveBeenCalledWith(
        affaireCollection,
        ...additionalAffaires.map(expect.objectContaining),
      );
      expect(comp.affairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Motif query and add missing value', () => {
      const workOrder: IWorkOrder = { id: 6339 };
      const motif: IMotif = { id: 24185 };
      workOrder.motif = motif;

      const motifCollection: IMotif[] = [{ id: 24185 }];
      jest.spyOn(motifService, 'query').mockReturnValue(of(new HttpResponse({ body: motifCollection })));
      const additionalMotifs = [motif];
      const expectedCollection: IMotif[] = [...additionalMotifs, ...motifCollection];
      jest.spyOn(motifService, 'addMotifToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ workOrder });
      comp.ngOnInit();

      expect(motifService.query).toHaveBeenCalled();
      expect(motifService.addMotifToCollectionIfMissing).toHaveBeenCalledWith(
        motifCollection,
        ...additionalMotifs.map(expect.objectContaining),
      );
      expect(comp.motifsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Employee query and add missing value', () => {
      const workOrder: IWorkOrder = { id: 6339 };
      const employees: IEmployee[] = [{ id: 1749 }];
      workOrder.employees = employees;

      const employeeCollection: IEmployee[] = [{ id: 1749 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [...employees];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ workOrder });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining),
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Article query and add missing value', () => {
      const workOrder: IWorkOrder = { id: 6339 };
      const articles: IArticle[] = [{ id: 24128 }];
      workOrder.articles = articles;

      const articleCollection: IArticle[] = [{ id: 24128 }];
      jest.spyOn(articleService, 'query').mockReturnValue(of(new HttpResponse({ body: articleCollection })));
      const additionalArticles = [...articles];
      const expectedCollection: IArticle[] = [...additionalArticles, ...articleCollection];
      jest.spyOn(articleService, 'addArticleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ workOrder });
      comp.ngOnInit();

      expect(articleService.query).toHaveBeenCalled();
      expect(articleService.addArticleToCollectionIfMissing).toHaveBeenCalledWith(
        articleCollection,
        ...additionalArticles.map(expect.objectContaining),
      );
      expect(comp.articlesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Vehicule query and add missing value', () => {
      const workOrder: IWorkOrder = { id: 6339 };
      const vehicules: IVehicule[] = [{ id: 32197 }];
      workOrder.vehicules = vehicules;

      const vehiculeCollection: IVehicule[] = [{ id: 32197 }];
      jest.spyOn(vehiculeService, 'query').mockReturnValue(of(new HttpResponse({ body: vehiculeCollection })));
      const additionalVehicules = [...vehicules];
      const expectedCollection: IVehicule[] = [...additionalVehicules, ...vehiculeCollection];
      jest.spyOn(vehiculeService, 'addVehiculeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ workOrder });
      comp.ngOnInit();

      expect(vehiculeService.query).toHaveBeenCalled();
      expect(vehiculeService.addVehiculeToCollectionIfMissing).toHaveBeenCalledWith(
        vehiculeCollection,
        ...additionalVehicules.map(expect.objectContaining),
      );
      expect(comp.vehiculesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Site query and add missing value', () => {
      const workOrder: IWorkOrder = { id: 6339 };
      const site: ISite = { id: 5680 };
      workOrder.site = site;

      const siteCollection: ISite[] = [{ id: 5680 }];
      jest.spyOn(siteService, 'query').mockReturnValue(of(new HttpResponse({ body: siteCollection })));
      const additionalSites = [site];
      const expectedCollection: ISite[] = [...additionalSites, ...siteCollection];
      jest.spyOn(siteService, 'addSiteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ workOrder });
      comp.ngOnInit();

      expect(siteService.query).toHaveBeenCalled();
      expect(siteService.addSiteToCollectionIfMissing).toHaveBeenCalledWith(
        siteCollection,
        ...additionalSites.map(expect.objectContaining),
      );
      expect(comp.sitesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const workOrder: IWorkOrder = { id: 6339 };
      const affaire: IAffaire = { id: 3095 };
      workOrder.affaire = affaire;
      const motif: IMotif = { id: 24185 };
      workOrder.motif = motif;
      const employee: IEmployee = { id: 1749 };
      workOrder.employees = [employee];
      const article: IArticle = { id: 24128 };
      workOrder.articles = [article];
      const vehicule: IVehicule = { id: 32197 };
      workOrder.vehicules = [vehicule];
      const site: ISite = { id: 5680 };
      workOrder.site = site;

      activatedRoute.data = of({ workOrder });
      comp.ngOnInit();

      expect(comp.affairesSharedCollection).toContainEqual(affaire);
      expect(comp.motifsSharedCollection).toContainEqual(motif);
      expect(comp.employeesSharedCollection).toContainEqual(employee);
      expect(comp.articlesSharedCollection).toContainEqual(article);
      expect(comp.vehiculesSharedCollection).toContainEqual(vehicule);
      expect(comp.sitesSharedCollection).toContainEqual(site);
      expect(comp.workOrder).toEqual(workOrder);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkOrder>>();
      const workOrder = { id: 28118 };
      jest.spyOn(workOrderFormService, 'getWorkOrder').mockReturnValue(workOrder);
      jest.spyOn(workOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workOrder }));
      saveSubject.complete();

      // THEN
      expect(workOrderFormService.getWorkOrder).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(workOrderService.update).toHaveBeenCalledWith(expect.objectContaining(workOrder));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkOrder>>();
      const workOrder = { id: 28118 };
      jest.spyOn(workOrderFormService, 'getWorkOrder').mockReturnValue({ id: null });
      jest.spyOn(workOrderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workOrder: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workOrder }));
      saveSubject.complete();

      // THEN
      expect(workOrderFormService.getWorkOrder).toHaveBeenCalled();
      expect(workOrderService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkOrder>>();
      const workOrder = { id: 28118 };
      jest.spyOn(workOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(workOrderService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAffaire', () => {
      it('Should forward to affaireService', () => {
        const entity = { id: 3095 };
        const entity2 = { id: 12389 };
        jest.spyOn(affaireService, 'compareAffaire');
        comp.compareAffaire(entity, entity2);
        expect(affaireService.compareAffaire).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMotif', () => {
      it('Should forward to motifService', () => {
        const entity = { id: 24185 };
        const entity2 = { id: 17092 };
        jest.spyOn(motifService, 'compareMotif');
        comp.compareMotif(entity, entity2);
        expect(motifService.compareMotif).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 1749 };
        const entity2 = { id: 1545 };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareArticle', () => {
      it('Should forward to articleService', () => {
        const entity = { id: 24128 };
        const entity2 = { id: 30377 };
        jest.spyOn(articleService, 'compareArticle');
        comp.compareArticle(entity, entity2);
        expect(articleService.compareArticle).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareVehicule', () => {
      it('Should forward to vehiculeService', () => {
        const entity = { id: 32197 };
        const entity2 = { id: 5417 };
        jest.spyOn(vehiculeService, 'compareVehicule');
        comp.compareVehicule(entity, entity2);
        expect(vehiculeService.compareVehicule).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSite', () => {
      it('Should forward to siteService', () => {
        const entity = { id: 5680 };
        const entity2 = { id: 7833 };
        jest.spyOn(siteService, 'compareSite');
        comp.compareSite(entity, entity2);
        expect(siteService.compareSite).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
