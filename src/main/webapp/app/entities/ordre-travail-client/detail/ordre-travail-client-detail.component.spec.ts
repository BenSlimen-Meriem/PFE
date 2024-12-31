import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OrdreTravailClientDetailComponent } from './ordre-travail-client-detail.component';

describe('OrdreTravailClient Management Detail Component', () => {
  let comp: OrdreTravailClientDetailComponent;
  let fixture: ComponentFixture<OrdreTravailClientDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrdreTravailClientDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./ordre-travail-client-detail.component').then(m => m.OrdreTravailClientDetailComponent),
              resolve: { ordreTravailClient: () => of({ id: 24849 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OrdreTravailClientDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrdreTravailClientDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ordreTravailClient on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OrdreTravailClientDetailComponent);

      // THEN
      expect(instance.ordreTravailClient()).toEqual(expect.objectContaining({ id: 24849 }));
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
