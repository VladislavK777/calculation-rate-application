import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {Observable} from 'rxjs';
import {BeginException} from "./begin-exception.model";

@Injectable({providedIn: 'root'})
export class BeginExceptionService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/begin-exception-setting');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  findAll(): Observable<BeginException[]> {
    return this.http.get<BeginException[]>(this.resourceUrl);
  }

  findById(id: number): Observable<BeginException> {
    return this.http.get<BeginException>(`${this.resourceUrl}/${id}`);
  }

  update(beginException: BeginException): Observable<BeginException> {
    return this.http.post<BeginException>(this.resourceUrl, beginException);
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }
}
