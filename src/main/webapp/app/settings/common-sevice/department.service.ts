import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {Road} from "./model/road.model";
import {Department} from "./model/department.model";
import {RequestSearch} from "./model/request-search.model";
import {createRequestOption} from "../../core/request/request-util";

@Injectable({providedIn: 'root'})
export class DepartmentService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/department');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  findAllByRoadId(roadId: number): Observable<Department[]> {
    return this.http.get<Department[]>(`${this.resourceUrl}/${roadId}`);
  }

  findAllByFilter(filter: RequestSearch): Observable<Department[]> {
    const options = createRequestOption(filter);
    return this.http.get<Department[]>(this.resourceUrl, {params: options});
  }
}
