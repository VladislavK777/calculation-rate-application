import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {SharedModule} from 'app/shared/shared.module';
import {TurnoverDayComponent} from "./list/turnover-day.component";
import {TurnoverDayEditComponent} from "./edit/turnover-day-edit.component";
import {turnoverDaySettingRoute} from './turnover-day-setting.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(turnoverDaySettingRoute)],
  declarations: [
    TurnoverDayComponent,
    TurnoverDayEditComponent,
  ],
})
export class TurnoverDaySettingModule {
}
