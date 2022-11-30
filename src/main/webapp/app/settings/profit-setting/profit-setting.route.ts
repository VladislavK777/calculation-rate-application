import {ActivatedRouteSnapshot, Resolve, Routes} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Profit} from "./service/profit.model";
import {ProfitService} from "./service/profit.service";
import {ProfitComponent} from "./list/profit.component";
import {ProfitEditComponent} from "./edit/profit-edit.component";

@Injectable({providedIn: 'root'})
export class ProfitResolve implements Resolve<Profit | null> {
  constructor(private service: ProfitService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Profit | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const profitSettingRoute: Routes = [
  {
    path: '',
    component: ProfitComponent,
  },
  {
    path: ':id/edit',
    component: ProfitEditComponent,
    resolve: {
      profit: ProfitResolve,
    },
  },
];
