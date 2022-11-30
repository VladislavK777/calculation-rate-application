import {ActivatedRouteSnapshot, Resolve, Routes} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {ReturnException} from "./service/return-exception.model";
import {ReturnExceptionService} from "./service/return-exception.service";
import {ReturnExceptionComponent} from "./list/return-exception.component";
import {ReturnExceptionEditComponent} from "./edit/return-exception-edit.component";

@Injectable({providedIn: 'root'})
export class ReturnExceptionResolve implements Resolve<ReturnException | null> {
  constructor(private service: ReturnExceptionService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<ReturnException | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.findById(id);
    }
    return of(null);
  }
}

export const returnExceptionSettingRoute: Routes = [
  {
    path: '',
    component: ReturnExceptionComponent,
  },
  {
    path: 'new',
    component: ReturnExceptionEditComponent,
    resolve: {
      returnException: ReturnExceptionResolve,
    },
  },
  {
    path: ':id/edit',
    component: ReturnExceptionEditComponent,
    resolve: {
      returnException: ReturnExceptionResolve,
    },
  },
];
