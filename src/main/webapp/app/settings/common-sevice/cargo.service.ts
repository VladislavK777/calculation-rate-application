import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {RequestSearch} from "./model/request-search.model";
import {Cargo} from "./model/cargo.model";
import {createRequestOption} from "../../core/request/request-util";

@Injectable({providedIn: 'root'})
export class CargoService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/cargo');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  searchCargo(filter: RequestSearch): Observable<Cargo[]> {
    const options = createRequestOption(filter);
    return this.http.get<Cargo[]>(this.resourceUrl, {params: options});
  }
}
