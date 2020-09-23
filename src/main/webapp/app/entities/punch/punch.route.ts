import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPunch, Punch } from 'app/shared/model/punch.model';
import { PunchService } from './punch.service';
import { PunchComponent } from './punch.component';
import { PunchDetailComponent } from './punch-detail.component';
import { PunchUpdateComponent } from './punch-update.component';

@Injectable({ providedIn: 'root' })
export class PunchResolve implements Resolve<IPunch> {
  constructor(private service: PunchService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPunch> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((punch: HttpResponse<Punch>) => {
          if (punch.body) {
            return of(punch.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Punch());
  }
}

export const punchRoute: Routes = [
  {
    path: '',
    component: PunchComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myLocationApp.punch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PunchDetailComponent,
    resolve: {
      punch: PunchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myLocationApp.punch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PunchUpdateComponent,
    resolve: {
      punch: PunchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myLocationApp.punch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PunchUpdateComponent,
    resolve: {
      punch: PunchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myLocationApp.punch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
