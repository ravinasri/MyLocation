import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyLocationTestModule } from '../../../test.module';
import { PunchDetailComponent } from 'app/entities/punch/punch-detail.component';
import { Punch } from 'app/shared/model/punch.model';

describe('Component Tests', () => {
  describe('Punch Management Detail Component', () => {
    let comp: PunchDetailComponent;
    let fixture: ComponentFixture<PunchDetailComponent>;
    const route = ({ data: of({ punch: new Punch(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyLocationTestModule],
        declarations: [PunchDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PunchDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PunchDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load punch on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.punch).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
