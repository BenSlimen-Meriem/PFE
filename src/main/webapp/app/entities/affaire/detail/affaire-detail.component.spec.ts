import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AffaireDetailComponent } from './affaire-detail.component';

describe('Affaire Management Detail Component', () => {
  let comp: AffaireDetailComponent;
  let fixture: ComponentFixture<AffaireDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AffaireDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./affaire-detail.component').then(m => m.AffaireDetailComponent),
              resolve: { affaire: () => of({ id: 3095 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AffaireDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AffaireDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load affaire on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AffaireDetailComponent);

      // THEN
      expect(instance.affaire()).toEqual(expect.objectContaining({ id: 3095 }));
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
