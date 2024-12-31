import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SSTService } from '../service/sst.service';
import { ISST } from '../sst.model';
import { SSTFormService } from './sst-form.service';

import { SSTUpdateComponent } from './sst-update.component';

describe('SST Management Update Component', () => {
  let comp: SSTUpdateComponent;
  let fixture: ComponentFixture<SSTUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sSTFormService: SSTFormService;
  let sSTService: SSTService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SSTUpdateComponent],
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
      .overrideTemplate(SSTUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SSTUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sSTFormService = TestBed.inject(SSTFormService);
    sSTService = TestBed.inject(SSTService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sST: ISST = { id: 32106 };

      activatedRoute.data = of({ sST });
      comp.ngOnInit();

      expect(comp.sST).toEqual(sST);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISST>>();
      const sST = { id: 28827 };
      jest.spyOn(sSTFormService, 'getSST').mockReturnValue(sST);
      jest.spyOn(sSTService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sST });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sST }));
      saveSubject.complete();

      // THEN
      expect(sSTFormService.getSST).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sSTService.update).toHaveBeenCalledWith(expect.objectContaining(sST));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISST>>();
      const sST = { id: 28827 };
      jest.spyOn(sSTFormService, 'getSST').mockReturnValue({ id: null });
      jest.spyOn(sSTService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sST: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sST }));
      saveSubject.complete();

      // THEN
      expect(sSTFormService.getSST).toHaveBeenCalled();
      expect(sSTService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISST>>();
      const sST = { id: 28827 };
      jest.spyOn(sSTService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sST });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sSTService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
