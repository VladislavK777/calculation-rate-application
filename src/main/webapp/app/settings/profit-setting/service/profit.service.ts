import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {Profit} from "./profit.model";

@Injectable({providedIn: 'root'})
export class ProfitService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/profit-setting');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  find(id: number): Observable<Profit> {
    return this.http.get<Profit>(`${this.resourceUrl}/${id}`);
  }

  findAll(): Observable<Profit[]> {
    return this.http.get<Profit[]>(this.resourceUrl);
  }

  update(profit: Profit): Observable<Profit> {
    return this.http.put<Profit>(this.resourceUrl, profit);
  }
}
