import {ActivatedRouteSnapshot, Resolve, Routes} from '@angular/router';
import {TurnoverDayComponent} from "./list/turnover-day.component";
import {TurnoverDayEditComponent} from "./edit/turnover-day-edit.component";
import {Injectable} from '@angular/core';
import {TurnoverDay} from "./service/turnover-day.model";
import {TurnoverDayService} from "./service/turnover-day.service";
import {Observable, of} from 'rxjs';

@Injectable({providedIn: 'root'})
export class TurnoverDayResolve implements Resolve<TurnoverDay | null> {
  constructor(private service: TurnoverDayService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<TurnoverDay | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const turnoverDaySettingRoute: Routes = [
  {
    path: '',
    component: TurnoverDayComponent,
  },
  {
    path: ':id/edit',
    component: TurnoverDayEditComponent,
    resolve: {
      turnoverDay: TurnoverDayResolve,
    },
  },
];
