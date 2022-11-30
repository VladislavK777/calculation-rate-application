import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {Road} from "./model/road.model";

@Injectable({providedIn: 'root'})
export class RoadService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/road');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  findAll(): Observable<Road[]> {
    return this.http.get<Road[]>(this.resourceUrl);
  }
}
