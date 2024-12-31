import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { GestionCoutDetailComponent } from './gestion-cout-detail.component';

describe('GestionCout Management Detail Component', () => {
  let comp: GestionCoutDetailComponent;
  let fixture: ComponentFixture<GestionCoutDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionCoutDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./gestion-cout-detail.component').then(m => m.GestionCoutDetailComponent),
              resolve: { gestionCout: () => of({ id: 19852 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GestionCoutDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionCoutDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load gestionCout on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GestionCoutDetailComponent);

      // THEN
      expect(instance.gestionCout()).toEqual(expect.objectContaining({ id: 19852 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
