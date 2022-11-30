import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'turnover-day',
        loadChildren: () => import('./turnover-day-setting/turnover-day-setting.module').then(m => m.TurnoverDaySettingModule),
      },
      {
        path: 'profit',
        loadChildren: () => import('./profit-setting/profit-setting.module').then(m => m.ProfitSettingModule),
      },
      {
        path: 'station',
        loadChildren: () => import('./station-setting/station-setting.module').then(m => m.StationSettingModule),
      },
      {
        path: 'begin-exception',
        loadChildren: () => import('./begin-exception-setting/begin-exception-setting.module').then(m => m.BeginExceptionSettingModule),
      },
      {
        path: 'return-exception',
        loadChildren: () => import('./return-exception-setting/return-exception-setting.module').then(m => m.ReturnExceptionSettingModule),
      },
    ]),
  ],
})
export class SettingsRoutingModule {
}
