import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {CargoClass} from "./model/cargo-classes.model";

@Injectable({providedIn: 'root'})
export class CargoClassService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/cargo-class');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  findAll(): Observable<CargoClass[]> {
    return this.http.get<CargoClass[]>(this.resourceUrl);
  }
}
