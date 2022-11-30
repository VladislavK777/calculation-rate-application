import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {RequestSearch} from "../../common-sevice/model/request-search.model";
import {Station} from "./station.model";
import {createRequestOption} from "../../../core/request/request-util";

@Injectable({providedIn: 'root'})
export class StationService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/station');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  findByCode(code: string): Observable<Station> {
    return this.http.get<Station>(`${this.resourceUrl}/byCode/${code}`);
  }

  findById(id: number): Observable<Station> {
    return this.http.get<Station>(`${this.resourceUrl}/byId/${id}`);
  }

  findAllByFilter(filter: RequestSearch): Observable<Station[]> {
    const options = createRequestOption(filter);
    return this.http.get<Station[]>(this.resourceUrl, {params: options});
  }

  update(profit: Station): Observable<Station> {
    return this.http.put<Station>(this.resourceUrl, profit);
  }
}
