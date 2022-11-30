import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {Observable} from 'rxjs';
import {ReturnException} from "./return-exception.model";

@Injectable({providedIn: 'root'})
export class ReturnExceptionService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/return-exception-setting');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  findAll(): Observable<ReturnException[]> {
    return this.http.get<ReturnException[]>(this.resourceUrl);
  }

  findById(id: number): Observable<ReturnException> {
    return this.http.get<ReturnException>(`${this.resourceUrl}/${id}`);
  }

  update(returnException: ReturnException): Observable<ReturnException> {
    return this.http.post<ReturnException>(this.resourceUrl, returnException);
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }
}
