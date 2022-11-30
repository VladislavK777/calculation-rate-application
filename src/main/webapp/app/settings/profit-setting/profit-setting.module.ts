import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {SharedModule} from 'app/shared/shared.module';
import {ProfitComponent} from "./list/profit.component";
import {profitSettingRoute} from "./profit-setting.route";
import {ProfitEditComponent} from "./edit/profit-edit.component";

@NgModule({
  imports: [SharedModule, RouterModule.forChild(profitSettingRoute)],
  declarations: [
    ProfitComponent,
    ProfitEditComponent,
  ],
})
export class ProfitSettingModule {
}
