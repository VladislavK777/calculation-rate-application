import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {SharedModule} from 'app/shared/shared.module';
import {StationComponent} from "./list/station.component";
import {stationSettingRoute} from "./station-setting.route";
import {StationEditComponent} from "./edit/station-edit.component";

@NgModule({
  imports: [SharedModule, RouterModule.forChild(stationSettingRoute)],
  declarations: [
    StationComponent,
    StationEditComponent,
  ],
})
export class StationSettingModule {
}
