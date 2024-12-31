import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MotifDetailComponent } from './motif-detail.component';

describe('Motif Management Detail Component', () => {
  let comp: MotifDetailComponent;
  let fixture: ComponentFixture<MotifDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MotifDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./motif-detail.component').then(m => m.MotifDetailComponent),
              resolve: { motif: () => of({ id: 24185 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MotifDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MotifDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load motif on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MotifDetailComponent);

      // THEN
      expect(instance.motif()).toEqual(expect.objectContaining({ id: 24185 }));
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
