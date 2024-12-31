import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { MotifService } from '../service/motif.service';
import { IMotif } from '../motif.model';
import { MotifFormService } from './motif-form.service';

import { MotifUpdateComponent } from './motif-update.component';

describe('Motif Management Update Component', () => {
  let comp: MotifUpdateComponent;
  let fixture: ComponentFixture<MotifUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let motifFormService: MotifFormService;
  let motifService: MotifService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MotifUpdateComponent],
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
      .overrideTemplate(MotifUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MotifUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    motifFormService = TestBed.inject(MotifFormService);
    motifService = TestBed.inject(MotifService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const motif: IMotif = { id: 17092 };

      activatedRoute.data = of({ motif });
      comp.ngOnInit();

      expect(comp.motif).toEqual(motif);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMotif>>();
      const motif = { id: 24185 };
      jest.spyOn(motifFormService, 'getMotif').mockReturnValue(motif);
      jest.spyOn(motifService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ motif });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: motif }));
      saveSubject.complete();

      // THEN
      expect(motifFormService.getMotif).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(motifService.update).toHaveBeenCalledWith(expect.objectContaining(motif));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMotif>>();
      const motif = { id: 24185 };
      jest.spyOn(motifFormService, 'getMotif').mockReturnValue({ id: null });
      jest.spyOn(motifService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ motif: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: motif }));
      saveSubject.complete();

      // THEN
      expect(motifFormService.getMotif).toHaveBeenCalled();
      expect(motifService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMotif>>();
      const motif = { id: 24185 };
      jest.spyOn(motifService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ motif });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(motifService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
