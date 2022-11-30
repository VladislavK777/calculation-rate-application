import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {CargoVolume} from "./model/cargo-volume.model";

@Injectable({providedIn: 'root'})
export class CargoVolumeService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/cargo-volume');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  findAll(): Observable<CargoVolume[]> {
    return this.http.get<CargoVolume[]>(this.resourceUrl);
  }
}
