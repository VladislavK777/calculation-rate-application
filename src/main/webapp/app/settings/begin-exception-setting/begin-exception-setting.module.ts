import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {SharedModule} from 'app/shared/shared.module';
import {BeginExceptionComponent} from "./list/begin-exception.component";
import {beginExceptionSettingRoute} from "./begin-exception-setting.route";
import {BeginExceptionEditComponent} from "./edit/begin-exception-edit.component";
import {BeginExceptionDeleteDialogComponent} from "./delete/begin-exception-delete-dialog.component";
import {BeginExceptionCopyComponent} from "./copy/begin-exception-copy.component";

@NgModule({
  imports: [SharedModule, RouterModule.forChild(beginExceptionSettingRoute)],
  declarations: [
    BeginExceptionComponent,
    BeginExceptionEditComponent,
    BeginExceptionCopyComponent,
    BeginExceptionDeleteDialogComponent
  ],
})
export class BeginExceptionSettingModule {
}
