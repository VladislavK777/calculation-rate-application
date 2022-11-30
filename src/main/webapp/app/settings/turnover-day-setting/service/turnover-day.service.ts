import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {TurnoverDay} from "./turnover-day.model";

@Injectable({providedIn: 'root'})
export class TurnoverDayService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/turnover-day-setting');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  find(id: number): Observable<TurnoverDay> {
    return this.http.get<TurnoverDay>(`${this.resourceUrl}/${id}`);
  }

  findAll(): Observable<TurnoverDay[]> {
    return this.http.get<TurnoverDay[]>(this.resourceUrl);
  }

  update(turnoverDay: TurnoverDay): Observable<TurnoverDay> {
    return this.http.put<TurnoverDay>(this.resourceUrl, turnoverDay);
  }
}
