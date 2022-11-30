import {ActivatedRouteSnapshot, Resolve, Routes} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Station} from "./service/station.model";
import {StationService} from "./service/station.service";
import {StationComponent} from "./list/station.component";
import {StationEditComponent} from "./edit/station-edit.component";

@Injectable({providedIn: 'root'})
export class StationResolve implements Resolve<Station | null> {
  constructor(private service: StationService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Station | null> {
    const code = route.params['code'];
    if (code) {
      return this.service.findByCode(code);
    }
    return of(null);
  }
}

export const stationSettingRoute: Routes = [
  {
    path: '',
    component: StationComponent,
  },
  {
    path: 'new',
    component: StationEditComponent,
    resolve: {
      station: StationResolve,
    },
  },
  {
    path: ':code/edit',
    component: StationEditComponent,
    resolve: {
      station: StationResolve,
    },
  },
];
