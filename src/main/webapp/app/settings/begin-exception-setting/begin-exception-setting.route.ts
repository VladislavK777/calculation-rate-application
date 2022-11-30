import {ActivatedRouteSnapshot, Resolve, Routes} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {BeginException} from "./service/begin-exception.model";
import {BeginExceptionComponent} from "./list/begin-exception.component";
import {BeginExceptionService} from "./service/begin-exception.service";
import {BeginExceptionEditComponent} from "./edit/begin-exception-edit.component";

@Injectable({providedIn: 'root'})
export class BeginExceptionResolve implements Resolve<BeginException | null> {
  constructor(private service: BeginExceptionService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<BeginException | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.findById(id);
    }
    return of(null);
  }
}

export const beginExceptionSettingRoute: Routes = [
  {
    path: '',
    component: BeginExceptionComponent,
  },
  {
    path: 'new',
    component: BeginExceptionEditComponent,
    resolve: {
      beginException: BeginExceptionResolve,
    },
  },
  {
    path: ':id/edit',
    component: BeginExceptionEditComponent,
    resolve: {
      beginException: BeginExceptionResolve,
    },
  },
];
