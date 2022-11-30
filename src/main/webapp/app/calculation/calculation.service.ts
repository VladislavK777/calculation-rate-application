import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {createRequestOption} from "../core/request/request-util";
import {RequestRout} from "./model/request-rout.model";
import {ExportModel} from "./model/export-model.model";

@Injectable({providedIn: 'root'})
export class CalculationService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/rout');
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  getRout(req: RequestRout): Observable<ExportModel> {
    const options = createRequestOption(req);
    return this.http.get<ExportModel>(this.resourceUrl, {params: options});
  }

  getGroupRouts(formData: FormData): Observable<ExportModel[]> {
    return this.http.post<ExportModel[]>(`${this.resourceUrl}/group`, formData);
  }

  downloadsExcel(body: ExportModel[]): Observable<Blob> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/download'), body, {responseType: 'blob'});
  }
}
