import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ISociete } from 'app/entities/societe/societe.model';
import { SocieteService } from 'app/entities/societe/service/societe.service';
import { SiteService } from '../service/site.service';
import { ISite } from '../site.model';
import { SiteFormService } from './site-form.service';

import { SiteUpdateComponent } from './site-update.component';

describe('Site Management Update Component', () => {
  let comp: SiteUpdateComponent;
  let fixture: ComponentFixture<SiteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let siteFormService: SiteFormService;
  let siteService: SiteService;
  let societeService: SocieteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SiteUpdateComponent],
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
      .overrideTemplate(SiteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SiteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    siteFormService = TestBed.inject(SiteFormService);
    siteService = TestBed.inject(SiteService);
    societeService = TestBed.inject(SocieteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Societe query and add missing value', () => {
      const site: ISite = { id: 7833 };
      const societe: ISociete = { id: 5934 };
      site.societe = societe;

      const societeCollection: ISociete[] = [{ id: 5934 }];
      jest.spyOn(societeService, 'query').mockReturnValue(of(new HttpResponse({ body: societeCollection })));
      const additionalSocietes = [societe];
      const expectedCollection: ISociete[] = [...additionalSocietes, ...societeCollection];
      jest.spyOn(societeService, 'addSocieteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ site });
      comp.ngOnInit();

      expect(societeService.query).toHaveBeenCalled();
      expect(societeService.addSocieteToCollectionIfMissing).toHaveBeenCalledWith(
        societeCollection,
        ...additionalSocietes.map(expect.objectContaining),
      );
      expect(comp.societesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const site: ISite = { id: 7833 };
      const societe: ISociete = { id: 5934 };
      site.societe = societe;

      activatedRoute.data = of({ site });
      comp.ngOnInit();

      expect(comp.societesSharedCollection).toContainEqual(societe);
      expect(comp.site).toEqual(site);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISite>>();
      const site = { id: 5680 };
      jest.spyOn(siteFormService, 'getSite').mockReturnValue(site);
      jest.spyOn(siteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ site });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: site }));
      saveSubject.complete();

      // THEN
      expect(siteFormService.getSite).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(siteService.update).toHaveBeenCalledWith(expect.objectContaining(site));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISite>>();
      const site = { id: 5680 };
      jest.spyOn(siteFormService, 'getSite').mockReturnValue({ id: null });
      jest.spyOn(siteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ site: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: site }));
      saveSubject.complete();

      // THEN
      expect(siteFormService.getSite).toHaveBeenCalled();
      expect(siteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISite>>();
      const site = { id: 5680 };
      jest.spyOn(siteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ site });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(siteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSociete', () => {
      it('Should forward to societeService', () => {
        const entity = { id: 5934 };
        const entity2 = { id: 31506 };
        jest.spyOn(societeService, 'compareSociete');
        comp.compareSociete(entity, entity2);
        expect(societeService.compareSociete).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
