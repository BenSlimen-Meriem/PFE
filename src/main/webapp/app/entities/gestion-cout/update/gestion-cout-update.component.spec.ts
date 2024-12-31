import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IPlanificateur } from 'app/entities/planificateur/planificateur.model';
import { PlanificateurService } from 'app/entities/planificateur/service/planificateur.service';
import { GestionCoutService } from '../service/gestion-cout.service';
import { IGestionCout } from '../gestion-cout.model';
import { GestionCoutFormService } from './gestion-cout-form.service';

import { GestionCoutUpdateComponent } from './gestion-cout-update.component';

describe('GestionCout Management Update Component', () => {
  let comp: GestionCoutUpdateComponent;
  let fixture: ComponentFixture<GestionCoutUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let gestionCoutFormService: GestionCoutFormService;
  let gestionCoutService: GestionCoutService;
  let planificateurService: PlanificateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GestionCoutUpdateComponent],
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
      .overrideTemplate(GestionCoutUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GestionCoutUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    gestionCoutFormService = TestBed.inject(GestionCoutFormService);
    gestionCoutService = TestBed.inject(GestionCoutService);
    planificateurService = TestBed.inject(PlanificateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call planificateur query and add missing value', () => {
      const gestionCout: IGestionCout = { id: 11524 };
      const planificateur: IPlanificateur = { id: 18052 };
      gestionCout.planificateur = planificateur;

      const planificateurCollection: IPlanificateur[] = [{ id: 18052 }];
      jest.spyOn(planificateurService, 'query').mockReturnValue(of(new HttpResponse({ body: planificateurCollection })));
      const expectedCollection: IPlanificateur[] = [planificateur, ...planificateurCollection];
      jest.spyOn(planificateurService, 'addPlanificateurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ gestionCout });
      comp.ngOnInit();

      expect(planificateurService.query).toHaveBeenCalled();
      expect(planificateurService.addPlanificateurToCollectionIfMissing).toHaveBeenCalledWith(planificateurCollection, planificateur);
      expect(comp.planificateursCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const gestionCout: IGestionCout = { id: 11524 };
      const planificateur: IPlanificateur = { id: 18052 };
      gestionCout.planificateur = planificateur;

      activatedRoute.data = of({ gestionCout });
      comp.ngOnInit();

      expect(comp.planificateursCollection).toContainEqual(planificateur);
      expect(comp.gestionCout).toEqual(gestionCout);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGestionCout>>();
      const gestionCout = { id: 19852 };
      jest.spyOn(gestionCoutFormService, 'getGestionCout').mockReturnValue(gestionCout);
      jest.spyOn(gestionCoutService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gestionCout });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gestionCout }));
      saveSubject.complete();

      // THEN
      expect(gestionCoutFormService.getGestionCout).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(gestionCoutService.update).toHaveBeenCalledWith(expect.objectContaining(gestionCout));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGestionCout>>();
      const gestionCout = { id: 19852 };
      jest.spyOn(gestionCoutFormService, 'getGestionCout').mockReturnValue({ id: null });
      jest.spyOn(gestionCoutService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gestionCout: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gestionCout }));
      saveSubject.complete();

      // THEN
      expect(gestionCoutFormService.getGestionCout).toHaveBeenCalled();
      expect(gestionCoutService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGestionCout>>();
      const gestionCout = { id: 19852 };
      jest.spyOn(gestionCoutService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gestionCout });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(gestionCoutService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePlanificateur', () => {
      it('Should forward to planificateurService', () => {
        const entity = { id: 18052 };
        const entity2 = { id: 1147 };
        jest.spyOn(planificateurService, 'comparePlanificateur');
        comp.comparePlanificateur(entity, entity2);
        expect(planificateurService.comparePlanificateur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
